package ver06;


//직장 전화번호부
public class PhoneCompanyInfor extends PhoneBookDataAbstract {
	
	String email; //이메일 - 필수 아님
	String company; //회사 
	
	PhoneCompanyInfor(String name,String phoneNumber,String email,String company){
		super(name,phoneNumber);
		this.email=email;
		this.company=company;
	}

	public void showData() {
		super.showData();
		System.out.println("이메일: "+this.email);
		System.out.println("회사: "+this.company);
	}
}
