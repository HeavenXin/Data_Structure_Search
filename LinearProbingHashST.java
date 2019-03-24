package LinearProbingHashST;
/**
 * 
 * @author Heaven
 *	使用并行数组,一个存储键,一个储存值,找不到继续找,找到更新
 * 	并且引用了一开始学习的动态数组调整
 */
public class LinearProbingHashST<Key,Value> {
	private int N;//键值对数量
	private int M;//散列表的大小
	private Key[] keys;//键
	private Value[] vals;//值
	public LinearProbingHashST() {//构造方法
		keys = (Key[])new Object[M];
		vals = (Value[])new Object[M];
	}
	public LinearProbingHashST(int X) {//新的构造方法,用于调整数组大小
		keys = (Key[])new Object[X];
		vals = (Value[])new Object[X];
	}
	private int hash(Key key) {//获取hash值
		return ((key.hashCode()&0x7fffffff)%M);//返回hash值	
	}
	private void resize(int i) {//动态调整大小
		LinearProbingHashST<Key, Value> temp;//创建一个新的平行数组		
		temp = new LinearProbingHashST<Key,Value>(i);
		for (int j = 0; j < M; j++) {
			if (keys[i]!=null) {
				temp.put(keys[i], vals[i]);
			}
		}
		keys = temp.keys;
		vals = temp.vals;
		N = temp.N;//在赋值回去
	}
	public void put(Key key,Value val) {//加入
		if (N>M/2) {//动态调整
			resize(M*2);
		}
		int i ;
		for (i = hash(key); keys[i]!= null; i=(i+1)%M) {//最后一步让其循环
			if (keys[i].equals(key)) {//已存在,且相同
				vals[i] = val;
				return ;
			}
		}
		keys[i] = key;
		vals[i] = val;
		N++;
	}
	public Value get(Key key) {
		for (int i = hash(key); keys[i]!= null; i=(i+1)%M) {
			if (keys[i].equals(key)) {//已存在,且相同
				return vals[i];
			}
		}
		return null;
	}
	public void delete(Key key) {
		if (!contains(key)) {//如果不存在
			return;
		}
		int i = hash(key);
		while(!key.equals(keys[i])) {//不等于
			i = (i+1)%M;//就前进
		}
		keys[i] =null;
		vals[i] =null;
		i = (i+1)%M;//向前一格
		while (keys[i]!=null) {//直到下一个空格,
			//这一步因为前面的让我们删除了,所以会自动向前一格
			Key keytemp = keys[i];
			Value valtemp = vals[i];
			N--;
			put(keytemp, valtemp);
			i = (i+1)%M;
		}
		N--;
		if (N>0&&N==M/8) {//动态调整
			resize(M/2);
		}
	}
	public boolean contains(Key key) {
		for (int i = hash(key); keys[i]!= null; i=(i+1)%M) {
			if (keys[i].equals(key)) {
				return true;
			}
		}
		return false;
	}
	
}
