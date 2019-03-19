package SequentialSearchST;

public class SequentialSearchST<Key,Value> {
	private Node first;//默认设置第一个
	private class Node{//定义链表
		Key key;
		Value val;
		Node next;
		public Node(Key key,Value val,Node next) {//构造方法
			this.key = key;
			this.val = val;
			this.next = next;	
		}
	}
	public Value get(Key key) {//查找方法
		for (Node x = first; x!=null;x=x.next) {//遍历
			if (key.equals(x.key)) {//如果相同键
				return x.val;
			}
		}
		return null;
	}
	public void put(Key key,Value val) {//添加的方法,先查找,有就更新,没就新建结点
		for (Node x = first; x!=null;x=x.next) {
			if (key.equals(x.key)) {
				x.val =val;
				return;//有的话
			}
		}
		first = new Node(key, val, first);//未命中,新建站点
	}
	public int size() {
		int count=0;
		for (Node x = first; x!=null;x=x.next) {
			count++;
		}
		return count;
	}
	public void delete(Key key) {
		for (Node x = first; x!=null;x=x.next) {	
			if (x.next.key.equals(key)) {//如果他下面的等于
				x.next = x.next.next;//直接删除,jvm自动回收
			}
		}
	}
	
}
