package ch13.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.naming.*;

public class BoardDBBean {
	private static BoardDBBean instance = new BoardDBBean(); 
	public static BoardDBBean getInstance() {
		return instance;
	}
	
	private BoardDBBean() {}
	
	private Connection getConnection() throws Exception {// 넣음
		Context initCtx = new InitialContext();
		Context envCtx = (Context)initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/oracledb");
		return ds.getConnection();
		}
	

	public void insertArticle(BoardDataBean article) throws Exception {
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int num = article.getNum();
		int ref = article.getRef();
		int re_step = article.getRe_step();
		int re_level = article.getRe_level();
		int number=0;
		String sql="";
		try {
			conn=getConnection();
			if(conn==null)
				System.out.println("fail");
			else
				System.out.println("Connected");
			sql="select max(num) from boarde";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				number = rs.getInt(1)+1; //해당컬럼의 가장첫번째에 있는 값을 가져옴
			}else {
				number=1;
			}
			closeDBResources(rs, pstmt);
			if(num!=0) { // 댓글
				sql = "update boarde set re_step=re_step+1 where ref=? and re_step>?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, re_step);
				pstmt.executeUpdate();
				re_step = re_step+1;
				re_level = re_level+1;
			} else { // 원본 글쓰기 - 댓글x
				ref=number;
				re_step=0;
				re_level=0;
				
			}
			closeDBResources(rs, pstmt);			
			// lhd.a for add filename
			sql="insert into boarde (num, writer, email, subject, password, reg_date, ";
			sql+=" ref, re_step, re_level, content, ip, filename) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, number);
            pstmt.setString(2, article.getWriter());
            pstmt.setString(3, article.getEmail());
            pstmt.setString(4, article.getSubject());
            pstmt.setString(5, article.getPassword());
			pstmt.setTimestamp(6, article.getReg_date());
            pstmt.setInt(7, ref);
            pstmt.setInt(8, re_step);
            pstmt.setInt(9, re_level);
			pstmt.setString(10, article.getContent());
			pstmt.setString(11, article.getIp());
			pstmt.setString(12, article.getFileName());
			pstmt.executeUpdate();

		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			closeDBResources(rs,pstmt,conn);
		}
	}
		public int getArticleCount() throws Exception {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;			
			int x = 0;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement("select count(*) from boarde");
				rs= pstmt.executeQuery();
				
				if(rs.next()) {
					x=rs.getInt(1);
					
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}finally {
				if(rs != null)try {rs.close();} catch(SQLException ex) {}
				if(pstmt != null) try {pstmt.close();} catch(SQLException ex) {}
				if(conn != null) try {conn.close();} catch(SQLException ex) {}
			}
			return x;
			
		}
		public List<BoardDataBean> getArticles(int start, int end)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<BoardDataBean> articleList=null;
			try {
				conn = getConnection();
				
				String sql = "select *"
						+"from (select ROWNUM rnum, B.*"
						+"from"
						+" (select * from boarde ORDER BY ref desc, re_step asc ) B)";
				sql+= "WHERE rnum >= ? and rnum<=?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					articleList = new ArrayList<BoardDataBean>(end);
					do {
						BoardDataBean article =new BoardDataBean();
						article.setNum(rs.getInt("num"));
						article.setWriter(rs.getString("writer"));
						article.setEmail(rs.getString("email"));
						article.setSubject(rs.getString("subject"));
						article.setPassword(rs.getString("password"));
						article.setReg_date(rs.getTimestamp("reg_date"));
						article.setReadcount(rs.getInt("readcount"));
						article.setRef(rs.getInt("ref"));
						article.setRe_step(rs.getInt("re_step"));
						article.setRe_level(rs.getInt("re_level"));
						article.setContent(rs.getString("content"));
						article.setIp(rs.getString("ip"));
						article.setFileName(rs.getString("filename"));
						
						articleList.add(article);
					}while(rs.next());
				}
			}catch(Exception ex) {
				ex.printStackTrace();
					
			}finally {
				if(rs != null)try {rs.close();} catch(SQLException ex) {}
				if(pstmt != null) try {pstmt.close();} catch(SQLException ex) {}
				if(conn != null) try {conn.close();} catch(SQLException ex) {}
			}
			return articleList;
		}
		public BoardDataBean getArticle(int num) throws Exception{
			Connection conn= null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			BoardDataBean article =null;
			try {
				conn = getConnection();
				
				pstmt = conn.prepareStatement("update boarde set readcount=readcount+1 where num=?");
				pstmt.setInt(1, num);
				pstmt.executeUpdate();
				
				
				pstmt = conn.prepareStatement("select * from boarde where num =?");
				pstmt.setInt(1, num);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					article = new BoardDataBean();
					article.setNum(rs.getInt("num"));
					article.setWriter(rs.getString("writer"));
					article.setEmail(rs.getString("email"));
					article.setSubject(rs.getString("subject"));
					article.setPassword(rs.getString("password"));
					article.setReg_date(rs.getTimestamp("reg_date"));
					article.setReadcount(rs.getInt("readcount"));
					article.setRef(rs.getInt("ref"));
					article.setRe_step(rs.getInt("ref"));
					article.setRe_level(rs.getInt("re_level"));
					article.setContent(rs.getString("content"));
					article.setIp(rs.getString("ip"));
					article.setFileName(rs.getString("filename"));
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}finally {
				if(rs != null) try {rs.close();} catch(SQLException ex) {}
				if(pstmt != null) try {pstmt.close();} catch(SQLException ex) {}
				if(conn!= null)try {conn.close();} catch(SQLException ex) {}
			}
			return article;
		}
		
		public BoardDataBean updateGetArticle(int num) throws Exception {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			BoardDataBean article=null;
			
			try {
				conn = getConnection();
				pstmt= conn.prepareStatement("select*from boarde where num=?");
				pstmt.setInt(1, num);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					article = new BoardDataBean();
					article.setNum(rs.getInt("num"));
					article.setWriter(rs.getString("writer"));
					article.setEmail(rs.getString("email"));
					article.setSubject(rs.getString("subject"));
					article.setPassword(rs.getString("password"));
					article.setReg_date(rs.getTimestamp("reg_date"));
					article.setReadcount(rs.getInt("readcount"));
					article.setRef(rs.getInt("ref"));
					article.setRe_step(rs.getInt("ref"));
					article.setRe_level(rs.getInt("re_level"));
					article.setContent(rs.getString("content"));
					article.setIp(rs.getString("ip"));
					
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}finally {
				if(rs != null) try {rs.close();} catch(SQLException ex) {}
				if(pstmt != null) try {pstmt.close();} catch(SQLException ex) {}
				if(conn!= null)try {conn.close();} catch(SQLException ex) {}
			}
			return article;
		}
		public int updateArticle(BoardDataBean article) throws Exception {
			Connection conn = null;
			PreparedStatement pstmt =null;
			ResultSet rs = null;
			
			String dbpasswd="";
			String sql="";
			int x=-1;
			try {
				conn = getConnection();
				
				pstmt=conn.prepareStatement("select password from boarde where num=?");
				pstmt.setInt(1, article.getNum());
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					dbpasswd = rs.getString("password");
					if(dbpasswd.equals(article.getPassword())) {
						sql="update boarde set writer=?, email=?, subject=?,password=?";
						sql+=",content=? where num=?";
						pstmt = conn.prepareStatement(sql);
						
						pstmt.setString(1, article.getWriter());
						pstmt.setString(2, article.getEmail());
						pstmt.setString(3, article.getSubject());
						pstmt.setString(4, article.getPassword());
						pstmt.setString(5, article.getContent());
						pstmt.setInt(6, article.getNum());
						pstmt.executeUpdate();
						x=1;
					}else {
						x=0;
					}
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}finally {
				if(rs != null) try {rs.close();} catch(SQLException ex) {}
				if(pstmt != null) try {pstmt.close();} catch(SQLException ex) {}
				if(conn!= null)try {conn.close();} catch(SQLException ex) {}
			}
			return x;
		}
		public int deleteArticle(int num, String passwd) throws Exception {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs= null;
			String dbpasswd="";
			int x =-1;
			try {
				conn = getConnection();
				pstmt= conn.prepareStatement("select password from boarde where num=?");
				pstmt.setInt(1, num);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					dbpasswd = rs.getString("password");
					if(dbpasswd.equals(passwd)) {
						pstmt = conn.prepareStatement("delete from boarde where num=?");
						pstmt.setInt(1, num);
						pstmt.executeUpdate();
						x=1; //글삭제 성공
					}else
						x=0; // 비밀번호 틀림
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}finally {
				if(rs != null) try {rs.close();} catch(SQLException ex) {}
				if(pstmt != null) try {pstmt.close();} catch(SQLException ex) {}
				if(conn!= null)try {conn.close();} catch(SQLException ex) {}
			}
			return x;
		}
		
		public String checkIdPw(String id) throws Exception{
			String dbpasswd = null;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs= null;
			try {
				conn = getConnection();
				pstmt= conn.prepareStatement("select password from login where id=?");
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					dbpasswd = rs.getString("password");					
				}
				
				}catch(Exception ex) {
					ex.printStackTrace();
				}finally {
					if(rs != null) try {rs.close();} catch(SQLException ex) {}
					if(pstmt != null) try {pstmt.close();} catch(SQLException ex) {}
					if(conn!= null)try {conn.close();} catch(SQLException ex) {}
			}
			return dbpasswd;
		}
		public int insertMember(member mb) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int result =0;
			String id = mb.getId();
			String password = mb.getPassword();
			String email = mb.getEmail();
			String name = mb.getName();
			String sql="";
			try {
				conn = getConnection();
				sql="insert into login(id,password,email,name,reg_date)";
				sql+= "values(?,?,?,?,?)"; 
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,mb.getId());
				pstmt.setString(2,mb.getPassword());
				pstmt.setString(3,mb.getEmail());
				pstmt.setString(4,mb.getName());
				pstmt.setTimestamp(5,mb.getReg_date());
				result = pstmt.executeUpdate();
			}catch(Exception ex){
				ex.printStackTrace();
				}finally {
					if(rs != null) try {rs.close();} catch(SQLException ex) {}
					if(pstmt != null) try {pstmt.close();} catch(SQLException ex) {}
					if(conn!= null)try {conn.close();} catch(SQLException ex) {}
			}
			return result;
			
		}
		public boolean deleteMember(String id, String password) {
			
			boolean result =false;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs= null;
			
			String dbPasswd="";
			try {
				conn=getConnection();
				String sql="select password from login where id=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					dbPasswd = rs.getString("password");
					if(dbPasswd.equals(password)) {
						String deletesql="delete from login where id=?";
						pstmt = conn.prepareStatement(deletesql);
						pstmt.setString(1, id);
						pstmt.executeUpdate();
						result=true;
					}
				}
				
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try {rs.close();} catch(SQLException ex) {}
			if(pstmt != null) try {pstmt.close();} catch(SQLException ex) {}
			if(conn!= null)try {conn.close();} catch(SQLException ex) {}
		
		}
			return result;
		}
		private void closeDBResources(ResultSet rs, Statement stmt, Connection conn ) {
			if (rs != null) {
				try {
					rs.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(stmt != null) {
				try {
					stmt.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		private void closeDBResources(ResultSet rs, PreparedStatement pstmt, Connection conn) {
			if (rs != null) {
				try {
					rs.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		private void closeDBResources(PreparedStatement pstmt, Connection conn) {		
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		private void closeDBResources(Statement stmt, Connection conn) {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		private void closeDBResources(ResultSet rs, PreparedStatement pstmt) {
			if (rs != null) {
				try {
					rs.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
					
		}	
}
