package arraystudy;

import java.util.Scanner;

public class MaxMin {

	public static int miniValue(int[] arr) {
		
		int min=arr[0]; // 기준 값을 첫번쨰 값으로 설정 
		for(int i=0;i<arr.length;i++) {
			if(arr[i]<min)
			{
				min=arr[i];
			}
		}
		return min;
		
	}
	
	public static int maxValue(int[] arr) {
		
		int max = 0;
		for(int i=0;i<arr.length;i++) {
			if(arr[i]>max)
			{
				max=arr[i];
			}
		}
		return max;
		
	}
	
	public static void main(String[] args) {
		
		int[] arr = new int[5];
		
		Scanner sc = new Scanner(System.in);
		
		for(int i=0;i<arr.length;i++) {
			System.out.println("숫자를 입력하세요: ");
			arr[i]=sc.nextInt();
		}
		
		//최소값
		int min = miniValue(arr);
		System.out.println("최소값: "+min);
		//최대값
		int max = maxValue(arr); 
		
		System.out.println("최대값: "+max);	

	}

}
