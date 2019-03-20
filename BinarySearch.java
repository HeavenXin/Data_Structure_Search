package BinarySearch_test;

import java.util.Scanner;


public class BinarySearch_test {
	public static int BinarySearch_test1(int[] a, int key) {
	//这次的目的是为了进行二分法
		int Fir = 0;
		int Las = a.length - 1;
		//之所以减一是因为索引为数量-1
		while(Fir<=Las) {
			int mid = (Fir + Las)/2;
			//从中间进行判断
			if (key > a[mid]) {
				Fir = mid+1;
			}else if (key < a[mid]) {
				Las = mid-1;
			}else {
				return mid;
			}	
		}
		return -1;
	}
	public static void main(String[] args) {
		int[]a = new int[100];
		for (int i = 0; i < a.length; i++) {
			a[i]=i;
		}
		Scanner scanner=new Scanner(System.in);
		System.out.println("输入一个1-100的数字");
		int key = scanner.nextInt();
		//直接调用方法
		if(BinarySearch_test1(a, key)>0) {
			System.out.println("该数字的索引为"+BinarySearch_test1(a, key));
		}else {
			System.out.println("抱歉请重新输入");
		}
		
		System.out.println((char)('a'+4));
	}
}
