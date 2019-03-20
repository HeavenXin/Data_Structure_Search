package BinarySearchST;

import Java.util.Queue;

public class BinarySearchST<Key extends Comparable<Key>,Value> {
	private Key[] keys;
	private Value[] vals;
	private int N;//实例变量
	public BinarySearchST(int amout) {
		keys = (Key[]) new Comparable[amout];
		vals = (Value[]) new Comparable[amout];	
		N =amout;
	}
	public Value get(Key key) {//查找的方法
		if(isEmpty())//如果数组为空
			return null;//直接返回
		int i = rank(key);//rank返回的数值为i
		if (i<N&&keys[i].compareTo(key)==0) {//两部验证,如果在范围内,且相同
			return vals[i];//返回值
		}else {
			return null;//不存在此值,返回null
		}
	}
	/**
	 *	这里需要使用二分查找法,基本的二分法基础上再返回不存在的情况时,小于key的数量
	 *
	 */
	public int rank(Key key) {
		int lo = 0;int hi = N-1;
		while (lo<=hi) {//只要小于
			int mid = lo+(hi-lo)/2;//获取中位
			int cmp = key.compareTo(keys[mid]);
			if (cmp>0) {//大于中位
				lo = mid +1;
			}else if(cmp <0) {
				hi = mid-1;
			}else {//返回中位
				return mid;
			}
		}
		return lo;//循环结束,返回小于的数量
	}
	public void put(Key key,Value val) {//查看是否含有,有就更新,没就创建
		int i =rank(key);//获取位置
		if (i<N&&keys[i].compareTo(key)==0) {//验证如果已经存在
			vals[i] = val;//替换
			return;//结束方法
		}
		for (int j = N; j>i; j--) {//先进行数组后移
			keys[j]=keys[j-1];
			vals[j]=vals[j-1];
		}//然后再插入
		keys[i] = key;
		vals[i] = val;
		N++;
	}
	public void delete(Key key) {
		int i =rank(key);
		if (!(i<N&&keys[i].compareTo(key)==0)) {//不存在
			return;
		}
		for (int j = i; j<N-1; j++) {//数组前移
			keys[j] = keys[j+1];
			vals[j] = vals[j+1];
		}
		N--;//N-1
	}
	public boolean isEmpty() {
		return N==0;
	}
	public boolean contains(Key key) {
		int i =rank(key);
		 return (i<N&&keys[i].compareTo(key)==0);
	}
	public int size() {
		return N;
	}
	public Key min() {
		return keys[0];
	}
	public Key max() {
		return keys[N-1];
	}
	public Key select(int k) {
		return	keys[k];//返回对应的键值
	}
	public Key celling(Key key) {//大于等于key的最小键
		int i = rank(key);
		return keys[i];
	}
	public Key floor(Key key) {//小于等于key的最大键
		int i = rank(key);
		return keys[i-1];
	}
	public Iterable<Key> keys(Key lo,Key hi){//我们创建一个链表来返回
		Queue<Key> queue  = new Queue<Key>();//创建一个新链表
		for (int i = rank(lo); i < rank(hi); i++) {
			queue.enqueue(keys[i]);
		}
		if (contains(hi)){//如果hi的点存在
			queue.enqueue(keys[rank(hi)]);
		}
		return queue;
	}
}
