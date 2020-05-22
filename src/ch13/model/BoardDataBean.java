package ch13.model;

import java.sql.Timestamp;
// MVC(Model(외부연계 - DB연동, filesystem연동.. 영상처리..)/view/ controller(servlet파일로 구성되고 화면전환-forward)
// model
// DB Connection - pool
// DB Access - DAO -CRUD (insert, delete, update, select)
// 내부연계
// view(JSP) 로 어떻게 전달할건데..
// VO(Value Objsect) = DTO ( Dat Transfer Object)

//void main() {
//	
//	request_Generate_Invoice(calculatePayroll(), 각급여결과, 전표번호, 급여일자, 실별자);
//}
//
//
//InvoiceDTO request_Generate_Inovice( int calPayroll, String wjavyqjsgh, String rmqdudlfwk, String tlrqufwk){
//	InvoiceDTO returnInvoiceDTO = requestInovice();
//	return InovoiceDTO;
//}






public class BoardDataBean {
	private int num; 
    private String writer;
    private String subject;
    private String email;
    private String content;
    private String password;
    private Timestamp reg_date;
    private int readcount;
    private String ip;
    private int ref;
    private int re_step;	
    private int re_level;
    private String fileName;
    
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String passwd) {
		this.password = passwd;
	}
	public Timestamp getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getRe_step() {
		return re_step;
	}
	public void setRe_step(int re_step) {
		this.re_step = re_step;
	}
	public int getRe_level() {
		return re_level;
	}
	public void setRe_level(int re_level) {
		this.re_level = re_level;
	}
    
}

