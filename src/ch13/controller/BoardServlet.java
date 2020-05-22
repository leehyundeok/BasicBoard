package ch13.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import ch13.model.BoardDBBean;
import ch13.model.BoardDataBean;
import ch13.model.member;

/**
 * Servlet implementation class BoardServlet
 */
@WebServlet("*.do")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String uri= request.getRequestURI();
		System.out.println("############ hdlee uri:" + uri);
		int lastIndex = uri.lastIndexOf("/");
		String action = uri.substring(lastIndex+1);
		System.out.println("############ hdlee action:" + action);
		String viewPage =null;
		if(action.equals("write.do")) {
			viewPage="writeForm.jsp";
		}
		if(action.equals("writerPro.do")) {
			// ejkim.a for file upload // ejkim.test
			String realFolder = "";//웹 어플리케이션상의 절대 경로
			String filename = "";
					
			//파일이 업로드되는 폴더를 지정한다.
			String saveFolder = "/fileSave";
			String encType = "utf-8"; //엔코딩타입
			int maxSize = 5*1024*1024;  //최대 업로될 파일크기 5Mb

			ServletContext context = getServletContext();
			//현재 jsp페이지의 웹 어플리케이션상의 절대 경로를 구한다
			realFolder = context.getRealPath(saveFolder);  
			MultipartRequest multi = null;
			try{
			  
			   multi = new MultipartRequest(request,realFolder, maxSize,encType,new DefaultFileRenamePolicy());			  
			   Enumeration<?> files = multi.getFileNames(); 
			   while(files.hasMoreElements()){
			      String name = (String)files.nextElement();
			      filename = multi.getFilesystemName(name);
			   }
			    BoardDataBean art = new BoardDataBean();
				art.setNum(Integer.parseInt(multi.getParameter("num")));
				art.setWriter(multi.getParameter("writer"));
				art.setSubject(multi.getParameter("subject"));
				art.setEmail(multi.getParameter("email"));
				art.setContent(multi.getParameter("content"));
				art.setPassword(multi.getParameter("password"));
				art.setRef(Integer.parseInt(multi.getParameter("ref")));
				art.setRe_step(Integer.parseInt(multi.getParameter("re_step")));
				art.setRe_level(Integer.parseInt(multi.getParameter("re_level")));
				art.setRe_level(Integer.parseInt(multi.getParameter("re_level")));
				art.setReg_date(new Timestamp(System.currentTimeMillis()));
				art.setIp(request.getRemoteAddr());
				// ejkim.a for file upload
				art.setFileName(filename);

				BoardDBBean dbPro = BoardDBBean.getInstance();
				dbPro.insertArticle(art);

			}catch(IOException ioe){
			 System.out.println(ioe);
			}catch(Exception ex){
			 System.out.println(ex);
			}
			
			viewPage="writerPro.jsp";
		}
		if(action.equals("list.do")) {
			
			
			viewPage="list.jsp";
		}
		if(action.equals("updatePro.do")) { 

			int check =0;
			String pageNum="";
			String realFolder="";
			String saveFolder="/fileSave";
			String encType="UTF-8";
			int maxSize = 5 * 1024 * 1024;
			ServletContext context = getServletContext();
			realFolder = context.getRealPath(saveFolder);
			MultipartRequest multi = null;
			
			
			try {
				multi = new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());
				Enumeration<?> files = multi.getFileNames();
				while (files.hasMoreElements()) {
				
					String name = (String) files.nextElement();
				}
				BoardDataBean art = new BoardDataBean();
				art.setNum(Integer.parseInt(multi.getParameter("num")));
				art.setWriter(multi.getParameter("writer"));
				art.setSubject(multi.getParameter("subject"));
				art.setEmail(multi.getParameter("email"));
				art.setContent(multi.getParameter("content"));
				art.setPassword(multi.getParameter("password"));
				System.out.println("############ hdlee pageNum:" + multi.getParameter("pageNum"));
				BoardDBBean dbPro = BoardDBBean.getInstance();
				check=dbPro.updateArticle(art); 
			}catch(Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("pageNum", multi.getParameter("pageNum"));
			request.setAttribute("check", check);
			viewPage="updatePro.jsp";
				
		}
			
		
			
		
		if(action.equals("deletePro.do")) {
			int check =0;
			int num = Integer.parseInt(request.getParameter("num"));
			String pageNum = request.getParameter("pageNum");
			String passwd = request.getParameter("password");
			try {
			BoardDBBean dbPro = BoardDBBean.getInstance();
			check = dbPro.deleteArticle(num, passwd);
			}catch(Exception e){
				e.printStackTrace();
			}
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("check", check);
			viewPage="deletePro.jsp";
		}
		if(action.equals("content.do")) {
			int num = Integer.parseInt(request.getParameter("num"));
			String pageNum = request.getParameter("pageNum");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		try{
				BoardDBBean dbPro = BoardDBBean.getInstance();
				BoardDataBean article = dbPro.getArticle(num);
				
				int ref=article.getRef();
				int re_step=article.getRe_step();
				int re_level=article.getRe_level();
				request.setAttribute("vo", article);
		}catch(Exception e) {
			e.printStackTrace();
		}
			request.setAttribute("pageNum", pageNum);
			viewPage="content.jsp";			
		}
		
		
		if(action.contentEquals("loginPro.do")) {
			String id= request.getParameter("id");
			String password = request.getParameter("password");
			String email= request.getParameter("email");
			//PrintWriter out = response.getWriter();
			//한글 깨짐현상 때문에 
			response.setContentType("text/html;charset=UTF-8");
			try {
				BoardDBBean dbPro = BoardDBBean.getInstance();
				String passwd = dbPro.checkIdPw(id);
				if(passwd == null) {
					//id 찾을수 없음
					request.setAttribute("checkId", -1);
					viewPage="login.jsp";
				}else if(password.equals(passwd)) {
					// 가져온 패스워드값과 동일하다면, 로그인 성공
					request.setAttribute("checkId", 0);
					request.getSession().setAttribute("id", id);
					request.getSession().setAttribute("password", password);
					request.getSession().setAttribute("email", email);
					// remove(로그아웃) 누르기 전까지 로그인 유지됨
					viewPage="list.jsp";
				}else {
					// 비밀번호틀림
					request.setAttribute("checkId", 1);
					/*
					 * out.print("<script type=text/javascript");
					 * out.print("alter('아이디와 패스워드가 일치하지 않습니다.');"); out.print("history.back();");
					 * out.print("</script>");
					 */
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		if(action.equals("insertTestForm.do")) {
			
			
				viewPage="insertTestForm.jsp";
		}
		if(action.contentEquals("insertTestPro.do")) {
			
			String id= request.getParameter("id");
			String email= request.getParameter("email");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			
			response.setContentType("text/html;charset=UTF-8");
			String str = "";
			
			int result =0;
			
			try{
				member mem = new member();
				mem.setId(id);
				mem.setEmail(email);
				mem.setPassword(password);
				mem.setName(name);
				Timestamp reg_date = new Timestamp(System.currentTimeMillis());
				
				
				BoardDBBean dbPro = BoardDBBean.getInstance();
				result = dbPro.insertMember(mem);
				
				
				}catch(Exception e) {
					e.printStackTrace();
				}
			request.setAttribute("id", id);
			request.setAttribute("email", email);
			request.setAttribute("password", password);
			request.setAttribute("name", name);
			request.setAttribute("str", str);
			request.setAttribute("result", result);
			viewPage="insertTestPro.jsp";
				
		}

		RequestDispatcher rDis = request.getRequestDispatcher(viewPage);
		rDis.forward(request, response);
	}
}


