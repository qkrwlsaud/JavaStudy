package Ticket;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;
import Movie.MovieManager;
import TimeTable.Seat;
import TimeTable.TimeTable;
import TimeTable.TimeTableManager;
import user.Info;
import user.MemberInfo;
import user.exception.OutOfNumberException;

public class TicketManager {

	TimeTableManager tManager = TimeTableManager.getInstatnce();
	MovieManager mManager = MovieManager.getInstance();
	Scanner sc = new Scanner(System.in);
	private int total = 0;	//총매출
	private int todayTotal = 0;	//오늘 매출
	private int serialNum = 2020000;
	private ArrayList<Ticket> todayTicketList;

	public TicketManager() {
		todayTicketList = new ArrayList<Ticket>();

	}

	public int chooseMovie() {
		int selectMovie = 0;
		for (int i = 0; i < mManager.getMovieList().size(); i++) {
			System.out.print(i + 1 + ") ");
			System.out.println(mManager.getMovieList().get(i).getTitle());
		}

		while (true) {
			try {
				System.out.print("영화 선택 >> ");
				selectMovie = Integer.parseInt(sc.nextLine().trim()) - 1;
				if (selectMovie >= mManager.getMovieList().size()) {
					OutOfNumberException e = new OutOfNumberException();
					throw e;
				}
			} catch (OutOfNumberException e) {
				System.out.println(e.getMessage());
				continue;
			} catch (Exception e) {
				System.out.println("잘못된 입력이에요");
				continue;
			}
			break;
		}
		return selectMovie;
	}

	public void creatTicket(Info info) {

		// 1. 영화 먼저 보여주고 영화 선택
		// 2. 선택한 영화에 따라

		Ticket ticket = null; // 예약 완료한 티켓
		TimeTable reserved_TimeTable = null; // 예약한 시간표
		Seat reserved_Seat = null; // 예약한 좌석

		ArrayList<TimeTable> choiceTableList;// 선택한 영화의 제목과 일치하는 시간표를 담을 리스트
		int selectMovie = 0; // 선택한 영화
		int selectTable = 0; // 선택한 시간표

		int selectRow = 0; // 선택한 좌석 열
		int selectSeat = 0; // 선택한 좌석 번호

		int choice = 0;
		int choicePoint = 0;
		int usePoint = 0;
		while (true) {
			System.out.println("1.영화정보보기");
			System.out.println("2.영화예매");
			try {
				choice = Integer.parseInt(sc.nextLine().trim());
				if (choice < 1 || choice > 2) {
					OutOfNumberException e = new OutOfNumberException();
					throw e;
				}
			} catch (OutOfNumberException e) {
				System.out.println(e.getMessage());
				continue;
			} catch (Exception e) {
				System.out.println("잘못된 입력이에요");
				continue;
			}
			break;
		}

		switch (choice) {
		case 1:
			while (true) {
				selectMovie = chooseMovie();
				mManager.getMovieList().get(selectMovie).showMovieInfo();
				System.out.println("이 영화로 예매하시겠어요?");
				int choice2 = 0;
				while (true) {
					System.out.println("1.YES\t2.다른영화선택");
					try {
						choice2 = Integer.parseInt(sc.nextLine().trim());
						if (choice2 < 1 || choice2 > 2) {
							OutOfNumberException e = new OutOfNumberException();
							throw e;
						}
					} catch (OutOfNumberException e) {
						System.out.println(e.getMessage());
						continue;
					} catch (Exception e) {
						System.out.println("잘못된 입력이에요");
						continue;
					}
					break;
				}
				if (choice2 == 1) {
					break;
				} else if (choice2 == 2) {
					continue;
				}
			}
			break;
		case 2:
			selectMovie = chooseMovie();
			break;
		}

		// 상영중인 영화 중에 사용자가 선택한 영화에 맞는 영화제목을 시간표리스트에서 찾아서 출력
		// 시간표 선택
		System.out.println("<선택한 영화로 검색한 상영시간표>");
		choiceTableList = new ArrayList<>();
		for (int i = 0; i < tManager.getTable().size(); i++) {
			if (mManager.getMovieList().get(selectMovie).getTitle()
					.equals(tManager.getTable().get(i).getMovie().getTitle())) {
				// 찾아서 다른 배열에 담아줌
				choiceTableList.add(tManager.getTable().get(i));
			}
		}
		for (int i = 0; i < choiceTableList.size(); i++) {
			System.out.println(i + 1 + ") ");
			choiceTableList.get(i).showTimeTable();
		}

		while (true) {
			try {
				System.out.print("시간표 선택 >> ");
				selectTable = Integer.parseInt(sc.nextLine().trim()) - 1;
				if (selectTable >= choiceTableList.size()) {
					OutOfNumberException e = new OutOfNumberException();
					throw e;
				}
			} catch (OutOfNumberException e) {
				System.out.println(e.getMessage());
				continue;
			} catch (Exception e) {
				System.out.println("잘못된 입력이에요");
				continue;
			}
			break;
		}
		reserved_TimeTable = choiceTableList.get(selectTable);

		// 선택한 시간표안에 해당 스크린의 좌석 배열을 출력 후
		// 좌석 열과 좌석 번호를 선택
		System.out.println("<좌석 선택>");
		reserved_TimeTable.getScreen().seatMap();

		while (true) {
			try {
				// 선택한 스크린의 좌석의 열(A,B,C...)의 개수만 큼 선택지 출력
				for (int i = 0; i < choiceTableList.get(selectTable).getScreen().getSeats().length; i++) {
					System.out.print(i + 1 + ") ");
					System.out.println((char) (i + 65) + "\t");
				}
				System.out.print("원하시는 좌석의 \"열\"을 골라주세요 \n>> ");
				selectRow = Integer.parseInt(sc.nextLine().trim());
				if (selectRow > choiceTableList.get(selectTable).getScreen().getSeats().length) {
					OutOfNumberException e = new OutOfNumberException();
					throw e;
				}

				System.out.print("원하시는 좌석의 \"번호\"를 골라주세요 \n>> ");
				selectSeat = Integer.parseInt(sc.nextLine().trim());
				if (selectSeat > choiceTableList.get(selectTable).getScreen().getSeats()[0].length) {
					OutOfNumberException e = new OutOfNumberException();
					throw e;
				}
			} catch (OutOfNumberException e) {
				System.out.println("좌석범위에 해당하지 않는 숫자에요.");
				System.out.println("\"열\"부터 다시 선택합니다");
				continue;
			} catch (Exception e) {
				System.out.println("잘못된 입력이에요");
				continue;
			}

			// 이미 예매가 된좌석이면 true 값이므로 예약좌석이라고 알려줌
			if (choiceTableList.get(selectTable).getScreen().getSeats(selectRow, selectSeat)) {
				System.out.println("이미 예약된 좌석이에요. 다시 선택해주세요");
				continue;
			}
			break;
		}

		// 선택한 시간표와 좌석의 정보, 티켓 가격 출력
		System.out.println("<선택하신 정보>");
		reserved_TimeTable.showTimeTable();
		System.out.println("선택한 좌석 : " + (char) (selectRow + 64) + "열 " + selectSeat);
		System.out.println("-------------------\n 티켓 가격 : " + getTicketPrice(reserved_TimeTable));

//		////////////////////////////
//		if (info.getPoint() > 0) {
//			System.out.println("현재 보유하고 계신 포인트는" + info.getPoint() + "점입니다.");
//			System.out.println("사용하시겠습니까? \n 1.네    2.아니오");
//			choicePoint = Integer.parseInt(sc.nextLine().trim());
//		}
//		if (choicePoint == 1) { // 포인트 사용
//			System.out.println("사용하실 포인트를 입력해주세요.");
//			usePoint = Integer.parseInt(sc.nextLine().trim());
//			System.out.println(usePoint + "를 사용하여 결제금액 " + (getTicketPrice(reserved_TimeTable) - usePoint) + "입니다.");
//			System.out.println("결제하시겠습니까?\n1.결제   2.다시 예매");
//
//			choicePoint = Integer.parseInt(sc.nextLine().trim());
//
//			if (choicePoint == 1) {
//
//				System.out.println(getTicketPrice(reserved_TimeTable) - usePoint + "원 결제합니다.\n 결제가 완료됐어요.");
//
//			}
//		} else { // 포인트 노사용
//
//		}

		////////////////////////////

		// 결제 하시겠어요?
		// 맴버 정보와 선택한 시간표값을 매개변수로 받아서 payment 메서드 실행
		if (payment(info, reserved_TimeTable) == 1) {
			return;
		}

		reserved_Seat = new Seat((char) (selectRow + 64), selectSeat);

		// 스크린에 좌석배열에 true로 만들어주는거
		tManager.getTable().get(selectTable).getScreen().setSeats(selectRow, selectSeat, true);

		String ticketNo = Integer.toHexString(serialNum);
		ticket = new Ticket(ticketNo, reserved_TimeTable, reserved_Seat);

		info.setTicket(ticket);// 생성된 티켓을 회원정보에 넣어줌

		addTicketList(ticket);

	}

	// 시간표를 받아서 그 시간표의 스크린이 가지고 있는 price를 반환
	public int getTicketPrice(TimeTable table) {
		return table.getScreen().getPrice();
	}

	// 예매하는 회원의 정보와 회원이 예매한 시간표를 받아서 결제를 해주는 메서드
	// int값 1을 반환시 결제 실패
	// int값 0을 반환시 결제 성공
	public int payment(Info info, TimeTable table) {
		int result = -1;
		int choice = 0;
		int choicePoint = 0;
		int finalPrice = getTicketPrice(table);
		int usePoint = 0;
		if (info.getPoint() > 0) {
			while (true) {
				System.out.println("현재 보유하고 계신 포인트는" + info.getPoint() + "점입니다.");
				try {
					System.out.println("포인트를 사용하시겠습니까? \n 1.네    2.아니오");
					choicePoint = Integer.parseInt(sc.nextLine().trim());
					if (choicePoint < 1 || choicePoint > 2) {
						OutOfNumberException e = new OutOfNumberException();
						throw e;
					}
				} catch (OutOfNumberException e) {
					System.out.println(e.getMessage());
					continue;
				} catch (Exception e) {
					System.out.println("잘못된 입력이에요");
					continue;
				}
				break;
			}
			if (choicePoint == 1) {
				while (true) {
					try {
						System.out.println("사용하실 포인트를 입력해주세요.");
						usePoint = Integer.parseInt(sc.nextLine().trim());
						if (usePoint > info.getPoint()) {
							System.out.println("포인트가 부족해요. 다시 입력해주세요");
							continue;
						}
					} catch (Exception e) {
						System.out.println("잘못된 입력이에요");
						continue;
					}
					break;
				}
				finalPrice = getTicketPrice(table) - usePoint;
				System.out.println(usePoint + "를 사용하여 결제금액 " + finalPrice + "입니다.");
			}
		}
		while (true) {
			System.out.println("결제하시겠어요?");
			System.out.println("1.결제\t2.다시 예매");
			try {
				choice = Integer.parseInt(sc.nextLine().trim());
				if (choice < 1 || choice > 2) {
					OutOfNumberException e = new OutOfNumberException();
					throw e;
				}
			} catch (OutOfNumberException e) {
				System.out.println(e.getMessage());
				continue;
			} catch (Exception e) {
				System.out.println("잘못된 입력이에요");
				continue;
			}
			break;
		}
		switch (choice) {
		case 1:
			if (info.getMyMoney() >= finalPrice) {
				System.out.println(finalPrice + "원 결제합니다");
				info.setMyMoney(info.getMyMoney() - finalPrice);
				System.out.println("결제금액의 5%가 포인트로 적립됩니다");
				info.setPoint(info.getPoint()-usePoint+(int)(finalPrice*0.05));
				System.out.println("결제가 완료됐어요");
				total += finalPrice;
				return result = 0;
			} else {
				System.out.println("잔액이 부족해요. 메인으로 돌아갈게요");
				return result = 1;

			}

		case 2:
			System.out.println("처음으로 돌아갑니다.");
			return result = 1;
		}
		return result;
	}

	// 삭제 : 회원이 가지고있는 티켓을 null값으로 변경
	public void delTicket(Info id) {
		int choice = 0;
		while (true) {
			System.out.println("정말로 예매를 취소하시겠어요?");
			System.out.println("1.YES\t2.No");
			try {
				choice = Integer.parseInt(sc.nextLine().trim());
				if (choice < 1 || choice > 2) {
					OutOfNumberException e = new OutOfNumberException();
					throw e;
				}
			} catch (OutOfNumberException e) {
				System.out.println(e.getMessage());
				continue;
			} catch (Exception e) {
				System.out.println("잘못된 입력이에요");
				continue;
			}
			break;
		}

		switch (choice) {
		case 1:

			// 1. 관리자가 가지고 있는 예매목록에서 삭제
			// 회원이 가지고있는 티켓과 관리자가 가지고있는 예매목록을 비교 후에 같으면 삭제
			for (int i = 0; i < todayTicketList.size(); i++) {
				if (id.getTicket() == todayTicketList.get(i)) {
					todayTicketList.remove(i);
				}
			}
			// 2. 환불 : 회원이 산 티켓 가격과 회원이 현재 보유한 금액을 더해서 set해줌
			id.setMyMoney(id.getMyMoney() + getTicketPrice(id.getTicket().gettTable()));
			System.out.println("환불 금액 :" + getTicketPrice(id.getTicket().gettTable()) + "원");
			System.out.println("현재 잔액 :" + id.getMyMoney() + "원");

			// 3. 회원이 선택한 좌석의 값을 false로 바꿔줌
			char selectRow = (char) (id.getTicket().getSeat().getRowNum() - 64);
			int selectSeat = id.getTicket().getSeat().getSeatNum();
			id.getTicket().gettTable().getScreen().setSeats(selectRow, selectSeat, false);

			// 4. 회원이 가진 티켓을 null로 만들기
			id.setTicket(null);
			System.out.println("삭제가 완료되었어요");
			break;
		case 2:
			System.out.println("메인으로 돌아갈게요");
			break;
		}

	}

	// 티켓리스트에 생성된 티켓 추가
	// serialCode +1
	public void addTicketList(Ticket tic) {
		if(todayTicketList.size()>0) {
			if(todayTicketList.get(todayTicketList.size()-1).date.get(Calendar.DATE)==tic.date.get(Calendar.DATE)) {
				todayTicketList = new ArrayList<Ticket>();
			}
		}
		todayTicketList.add(tic);
		serialNum += 20;
	}

	// 예매 현황 및 매출 출력해주는 메서드
	public void showAllTicket() {
		// 1. 예매된 티켓의 총 개수 출력
		System.out.println("오늘 예매 티켓 수 : " + todayTicketList.size());
		
		// 2. 예매된 티켓들의 가격을 더해주고
		for (int i = 0; i < todayTicketList.size(); i++) {
			todayTotal += getTicketPrice(todayTicketList.get(i).gettTable());
		}
		// 3. 매출 출력
		System.out.println("일 매출 : "	+ todayTotal);
		System.out.println("총 매출 : " + total);

	}

	public ArrayList<Ticket> getTickets() {
		return todayTicketList;
	}
	
	public int getTotal() {
		return total;
	}

}
