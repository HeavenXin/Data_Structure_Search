package SeparateChainingHashST;
/**
 * @author Heaven
 *	
 *	此方法在基于链表之上,我们先将散列范围的数创建一个数组,每个数组对应一个链表
 *	利用链表避免了键位冲突
 *	
 * 	导入了之前创建的链表查找法
 * 	
 * 	再使用动态调整其数组
 */

import java.util.HashMap;

import SequentialSearchST.SequentialSearchST;

public class SeparateChainingHashST <Key,Value>{
	private int N;//键值对数量
	private int M;//散列表的大小
	private SequentialSearchST <Key,Value>[] st;//数组
	public SeparateChainingHashST() {
		this(997);//默认997个
	}
	public SeparateChainingHashST(int i) {//创建链表
		this.M = i;
		st = (SequentialSearchST <Key,Value>[])new  SequentialSearchST[i];
		//创建一个链表数组
		for(int j =0;j<st.length;j++) {
			st[j] = new SequentialSearchST();//对应创建链表
		}
		
	}
	private int hash(Key key) {//hash值计算
		return ((key.hashCode()&0x7fffffff)%M);
	}
	public Value get(Key key) {//获取值
		
		return (st[hash(key)].get(key));//调用链表查找
	}
	public void put(Key key,Value val) {//输入
		if (N>M/2) {
			resize(M*2);
		}
		st[hash(key)].put(key, val);
		N++;
	}
	public void delete(Key key) {
		if (N>0&&N<M/8) {
			resize(M/8);
		}
		st[hash(key)].delete(key);//进行删除
		N--;
	}
	private void resize(int i) {//动态调整 
		SequentialSearchST<Key, Value>[] temp = (SequentialSearchST <Key,Value>[])new  SequentialSearchST[i];//创建
		for (int j = 0; j < st.length; j++) {
			temp[j] = st[j];
		}
	}

	
}
