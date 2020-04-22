package arraystudy;

import java.util.Scanner;

public class ArrayEx {

	//2차원 배열의 모든 요소 값 더하기 
	public static void addOneDArr(int[][] arr,int add) {
		for(int i=0;i<arr.length;i++) {
			for(int j=0;j<arr[i].length;j++) {
				arr[i][j] += add;
				System.out.print(arr[i][j]+" ");
			}System.out.println();
		}
	}
	
	public static void main(String[] args) {
		
		int[][] arr = new int[2][2];
		
		Scanner sc = new Scanner(System.in);
		
		for(int i=0;i<arr.length;i++) {
			for(int j=0;j<arr[i].length;j++) {
				System.out.println("값을 입력하세요");
				arr[i][j]= sc.nextInt();
			}
			
		}
		
		addOneDArr(arr, 1);		
	}

}
