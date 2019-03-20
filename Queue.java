package Queue;


public class Queue<Item> {//使用了泛型的概念
	private Node start;//开始位和结束位
	private Node end;
	private int N;//元素的数量
	private class Node{
		Item item;
		Node next;//栈的位置
	}
	public boolean isEmpty() {//两个常用方法
		return N==0;
	}
	public int size() {
		return N;
	}
	public void enqueue(Item item) {//设置添加方法
		Node oldend = end;
		end.item = item;
		end.next = null;//从尾端插入
		if (isEmpty()) {
			start = end;
		}else {
			oldend.next = end;
		}
		N++;
	}
	public Item dequeue() {
		Item item = start.item;
		start = start.next; //从开头段删除
		if (isEmpty()) {
			end = null;
		}
		N--;
		return item;
	}
}
