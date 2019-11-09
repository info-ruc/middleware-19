package junit.test;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import sop.dao.domain.Bid;
import sop.dao.domain.Company;
import sop.dao.domain.Project;
import sop.service.CompanyService;
import sop.service.impl.CompanyServiceImpl;

public class CompanyServiceTest {

	@Test
	public void testRegister(){
		CompanyService cs =new CompanyServiceImpl();
		Company c=new Company();
		c.setCid("1");
		c.setCompanyName("cn");
		c.setAccount("1");
		c.setPassword("11");
		c.setType("1");
		cs.registerCompany(c);
	}
	
	@Test
	public void testIssueProject(){
		CompanyService cs =new CompanyServiceImpl();
		Project p=new Project();
		p.setProId("2");
		cs.issueProject(p, "1");
	}
	
	@Test
	public void testAffirmBid(){
		CompanyService cs =new CompanyServiceImpl();
		System.out.println();
		cs.affirmBid("1", "1");
	}
	
	@Test
	public void testGetAllBid(){
		CompanyService cs=new CompanyServiceImpl();
		ArrayList<Bid> ps=cs.getAllMyBid("1");
		Iterator<Bid> it= ps.iterator();
		while(it.hasNext()){
			System.out.println(it.next().getBidId());
		}
	}

//	@Test
//	public void testGetCompanyInfo(){
//		CompanyService cs =new CompanyServiceImpl();
//		Company c=cs.getCompanyInfo("1");
//		System.out.println(c.getAccount()+c.getPassword()+c.getCompanyName());
//	}
	
	@Test
	public void testModifyCompanyInfo(){
		CompanyService cs =new CompanyServiceImpl();
		Company c=new Company();
		c.setCid("7");
		c.setCompanyName("ncn");
		c.setAccount("7");
		c.setPassword("77");
		c.setType("1");
		cs.modifyCompanyInfo(c);
	}
	
	@Test
	public void testGetAllProject(){
		CompanyService cs=new CompanyServiceImpl();
		ArrayList<Project> ps=cs.getAllMyProject("c2");
		Iterator<Project> it= ps.iterator();
		while(it.hasNext()){
			System.out.println(it.next().getProId());
		}
	}
	
	@Test
	public void testGetAllMyBid(){
		CompanyService cs=new CompanyServiceImpl();
		ArrayList<Bid> bs=cs.getAllMyBid("c22016-10-26 16:39:47");
		Iterator<Bid> it= bs.iterator();
		while(it.hasNext()){
			System.out.println(it.next().getBidId());
		}
	}
	

}
