import java.sql.Connection;
import java.sql.DriverManager;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//스프링 테스트 클래스를 생성하기 위한 어노테이션
@RunWith(SpringJUnit4ClassRunner.class)
//스프링 설정 파일이나 클래스를 실행하도록 해주는 어노테이션 
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})

public class SamplingTesting {
	//동일한 자료형의 객체가 있으면 자동으로 대입 받는 어노 테이션
	//보통은 @Autowired를 많이 이용 
	@Inject
	private DataSource ds;
	
	@Test
	public void dataSource() {
		try {
			Connection con = ds.getConnection();
			System.out.println("con:"+con);
		}catch(Exception e) {
			System.out.println("데이터 소스 예외:" +e.getMessage());
			e.printStackTrace();
		}
	}
	
	//데이터베이스 연결을 테스트할 메소드
	@Test
	public void connectionTest() {
		//데이터베이스 접속에 필용한 정보 
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@192.168.0.100:1521/XEPDB1";
		String user = "user12";
		String pw = "user12";
			
		try {
			//데이터베이스 드라이버 로드 - 처음 한 번 만 수행
			Class.forName(driver);
			//연결
			Connection con = DriverManager.getConnection(url,user,pw);
			//연결확인
			System.out.println("연결:" + con);
			//연결해제
			con.close();
		} catch (Exception e) {
			System.out.println("데이터베이스 접속 예외 :" + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	//동일한 자료형의 Bean이 있으면 자동으로 주입받는 어노테이션
	@Autowired
	//SqlSessionTemplate 인스턴스를 주입받기 위한 변수 생성
	//SqlSession이 SqlSessionTemplate의 상위클래스 이므로 
	//보통 SqlSession으로 선언해서 사용
	private SqlSession sqlSession;
	
	@Test
	public void myBatisTest() {
		System.out.println(sqlSession);
	}
	
}
