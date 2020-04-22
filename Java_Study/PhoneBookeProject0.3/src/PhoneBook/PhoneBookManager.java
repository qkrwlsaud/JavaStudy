package PhoneBook;

import java.util.Scanner;

public class PhoneBookManager {
	
	Scanner sc;
	PhoneBookData data; //PhoneBookData 참조변수
	PhoneBookData[] phoneData; //PhoneBookData의 객체를 저장한 배열 
	int length; // 객체 배열의 길이 
	int index; //이름 검색 시 참조 할객체 배열의 인덱스 값
	
	//Manager 생성자 
	public PhoneBookManager(){
		sc=new Scanner(System.in);
		data=null;
		phoneData=new PhoneBookData[100];
		length=0;
		index=-1;
		
	}

	
	//Data 객체 생성 
	PhoneBookData instance() {
		
		//이름 입력
		System.out.println("이름을 입력하세요");
		String name=sc.nextLine();
		if(name.trim().isEmpty()){
			System.out.println("이름은 필수 항목입니다. 이름을 다시 입력하세요.");
			name=sc.nextLine();
		}
		//전화번호 입력
		System.out.println("전화번호를 입력하세요");
		String phoneNumber=sc.nextLine();
		if(phoneNumber.trim().isEmpty()){
			System.out.println("전화번호는 필수 항목입니다. 전화번호를 다시 입력하세요.");
			phoneNumber=sc.nextLine();
		}
		//생년월일 입력
		System.out.println("생년월일을 입력하세요");
		String birthday=sc.nextLine();
		
		//객체 생성 
		data = new PhoneBookData(name,phoneNumber,birthday);
		if(birthday==null||birthday.trim().isEmpty()){ //생일 입력값이 없음,공백을제거
			data=new PhoneBookData(name,phoneNumber);
		}		
		return data;
	}//instance 
	
	
	//데이터 저장
	void insertData() {
		phoneData[length] = instance();
		length++;
	}
	
	
	//데이터 전체 출력 
	void showAllData() {
		for(int i=0;i<length;i++) {
			phoneData[i].showData();
			System.out.println("------------------------");
		}
	}
	
	
	//이름으로 데이터 검색 
	int searchName(String nameInput) {
		
		int index =-1;
		//객체 배열에 이름이 존재하는지 확인 
		for(int i=0;i<length;i++) { //phoneData 배열 반복
			if(phoneData[i].name(nameInput)) { //phoneData객체 배열에 이름이 같을 때 
				System.out.println("========\""+nameInput+"\"님 정보======="); // 출력
				index = i; // index값을 변경 
				}
			}
		
		return index;
	}
	
	
	//데이터 검색
	void selectData() {
		
		System.out.println("검색 할 이름을 입력하세요");
		String nameInput= sc.nextLine();
		 

		index = searchName(nameInput);
		
		//index값을 이용하여 검색된 이름의 데이터 값 출력 
		if(index<0) {
			System.out.println("찾으시는 이름이 없습니다.");
		}else {
			phoneData[index].showData();//객체 배열의 index값에 저장된 주소값을 참조하여 출력 메서드 호출 
		}

	}
	
	//데이터 수정
	void update() {
		
		System.out.println("수정할 이름을 입력하세요");
		String nameInput = sc.nextLine();
		
		index = searchName(nameInput);
		
		if(index<0) {
			System.out.println("찾으시는 이름이 없습니다.");
		}else {
			
			phoneData[index].showData(); // 이름으로 찾은 정보 
			while(true) {
				
			System.out.println("--------------------------");
			System.out.println("<<<<수정할 정보를 선택하세요>>>>");
			System.out.println("1.이름");
			System.out.println("2.전화번호");
			System.out.println("3.생년월일");
			System.out.println("4.메뉴로 이동");
			
			//수정 메뉴 선택
			int updateMenu = sc.nextInt();
			sc.nextLine();
				
			switch(updateMenu) {
			case 1:
				System.out.println("수정할 이름을 입력하세요");
				phoneData[index].name=sc.nextLine();
				while(true) {
				if(phoneData[index].name.trim().isEmpty()) {//이름 값을 입력하지 않했을 때 조건 실행 
					System.out.print("이름은 필수 항목입니다.");
					System.out.println("수정할 이름을 입력하세요 ");
					phoneData[index].name=sc.nextLine();
				 }else {
					 break;
				 }
					
				}
				System.out.println("\""+nameInput+"\"님의 이름은\" "+phoneData[index].name+"\"로 수정되었습니다.");
				break; //switch문 종료 
				
			case 2:
				System.out.println("수정할 전화번호을 입력하세요");
				phoneData[index].phoneNumber=sc.nextLine();
				while(true) {
				if(phoneData[index].phoneNumber.trim().isEmpty()) {//생년월일 값을 입력 안했을 때 조건 실행
					System.out.print("전화번호는 필수 항목입니다.");
					System.out.println("수정할 전화번호를 입력하세요");
					phoneData[index].phoneNumber=sc.nextLine();
				}else {
					 break;
				 }
				}
					System.out.println("\""+nameInput+"\"님의 번호가 \" "+phoneData[index].phoneNumber+"\"로 수정되었습니다.");
				break;

			case 3:
				System.out.println("수정할 생년월일을 입력하세요");
				phoneData[index].birthday=sc.nextLine();
				System.out.println("\""+nameInput+"\"님의 생년월일 입력값이 없습니다.");
				break;
				
			case 4:
				return; //수정완료 후 메뉴 화면으로 돌아감 
			
			}//switch문 
				
			}//while문

		}
		
	}
	
	void deleteCheck(String nameInput,String delete) {
		
		if(delete.equals("Yes")||delete.equals("yes")) {
			for(int i=index;i<length;i++) {
				phoneData[i] = phoneData[i+1]; // 삭제된 배열에 인덱스 i+1의 값 저장 
			}
			length --; //배열이 삭제되어 배열의 길이가 줄어듬 
			
			System.out.println("\""+nameInput+"\"님의 정보가 삭제되었습니다.");
			return;
		} else if(delete.equals("No")||delete.equals("no")) {
			System.out.println("\""+nameInput+"\"님의 정보가 삭제되지 않았습니다.");
			return;
		} 
	}
	
	//데이터 삭제
	void delete() {
		
		System.out.println("삭제할 이름을 입력하세요");
		String nameInput = sc.nextLine();
		
		index = searchName(nameInput);
		
		if(index<0) {
			System.out.println("찾으시는 이름이 없습니다.");
		}else {
			
			phoneData[index].showData(); //데이터 출력
			
			//삭제 여부 질문
			System.out.println("삭제하겠습니까? Yes or No");
			String delete = sc.nextLine();
			
			
			//yes or no 
//			deleteCheck(nameInput,delete);
			
			
			while(true) {
		
				if(delete.trim().isEmpty()) {
					System.out.println("Yes(yes) 또는 No(no) 입력하세요");
					delete = sc.nextLine();
				} else {
					deleteCheck(nameInput,delete);
					break;
				}
				
			}
			
			}
		
			
		}
		
		
	}


