package BinarySearchTree;


/**
 * 
 * @author Heaven
 *
 *	实现二分查找树,一个二叉树中,每个结点都含有一个comparable的键,
 *		每个结点的键都大于其左子树的任意节点且小于其右子树的任意节点
 *		本文件基本完成了一个有序数组所需的api
 */

public class BST<Key extends Comparable<Key>,Value> {
	private Node root;//创建根结点
	private class Node{//创建内部类
		private Key key;//结点的键;
		private Value value;//结点的值;
		private int N;//子节点的数量
		private Node left,right;//左右节点
		public Node(Key key,Value value,int N) {//构造类
			this.key =key;
			this.value =value;
			this.N =N;
		}	
	}
	public Value get(Key key) {//获取对应键的值
		return get(root,key);
	}
	private Value get(Node node, Key key) {//重写
		if (node ==null) {//如果不存在,直接返回null
			return null;
		}
		int i = node.key.compareTo(key);//比较大小
		if (i>0) {
			return get(node.right, key);//大就去比较右边
		}else if(i<0) {
			return get(node.left, key);//反之亦然
		}
		return node.value;//等于返回值
	}
	public void put(Key key,Value value) {//插入,如果存在更新,不存在插入
		root = put(root,key, value);
	}
	private Node put(Node node, Key key, Value value) {
		if(node == null) {//如果根不存在
			return new Node(key, value, 1);
		}
		int i = node.key.compareTo(key);//比较大小
		if (i>0) {
			put(node.right, key, value);
		}else if (i<0) {
			put(node.left, key, value);
		}else {
			node.value =value;//找到就更新
		}
		node.N = size(node.left)+size(node.right)+1;//在结束递归时,向上搜索更新并+1
		return node;//返回 上一层node
	}
	public int rank(Key key) {//获取小于键的数量
		return rank(root,key);//还必须用int类型,一开始本来想用node类型
	}
	private int rank(Node node,Key key) {
		if (node==null) {//为空则为0
			return 0;
		}
		int i = node.key.compareTo(key);
		if (i>0) {
			return rank(node.left,key);//向左边递归
		}else if (i<0) {
			return rank(node.right, key)+size(node.left)+1;//向右递归,还要加上左边的
		}else {
			return size(node.left);//返回左边的数量
		}
		
	}
	public Key select(int k) {//排名为k的键
		return select(root,k).key;
	}
	private Node select(Node node, int k) {//查找方法
		if(node == null) {
			return null;
		}
		int l = size(node.left);//获取左边的
		if (l>k) {
			return select(node.left,k);
		}
		if (l==k) {
			return node;
		}
		else {
			return select(node.right,k);
		}
	}
	public void deldete(Key key) {
		root = deldete(root, key);
	}
	private Node deldete(Node x,Key key){
		if (x == null) {
			return null;//先排查为空
		}
		int i = x.key.compareTo(key);
		if (i>0) {
			deldete(x.left, key);
		}else if (i<0) {
			deldete(x.right, key);
		}else {//相等的情况,开始了
			//忘了考虑如果已经是最底层的情况
			if (x.right == null) {
				return x.left;
			}
			if (x.left == null) {
				return x.right;
			}
			Node temp = x;
			x = min(temp.right);//找到右边最小的
			x.left = temp.left;
			x.right = deleteMin(temp.right);//删除右边的最小,并且会递归回来
		}
		x.N = size(x.left)+size(x.right)+1;
		return x;
	}
	public void deleteMax() {
		root = deleteMax(root);
	}
	private Node deleteMax(Node node) {//删除最大值
		if (node.right ==null) {
			return node.left;//返回左边的
		}
		node.right =deleteMax(node.right);//使原本左边等于右边的
		node.N = size(node.left)+size(node.right)+1;//每返回一层,就增加结点+1
		return node;//返回到root
	}
	public void deleteMin() {
		root = deleteMin(root);
	}
	private Node deleteMin(Node node) {//删除最小值
		if (node.left ==null) {
			return node.right;//返回左边的
		}
		node.left =deleteMax(node.left);//使原本左边等于右边的
		node.N = size(node.left)+size(node.right)+1;//每返回一层,就增加结点+1
		return node;//返回到root
	}
	public Key floor(Key key) {//查找小于等于key的最大键
		Node x = floor(root, key);//我自己做的并不严谨,忘了考虑空的情况,特此修改
		if (x == null) {
			return null;
		}
		return x.key;
	}
	private Node floor(Node node,Key key) {
		if (node == null) {
			return null;
		}//为空直接放
		int i = node.key.compareTo(key);//进行比对
		if (i==0) {
			return node;//相同直接返回
		}
		if (i < 0) {
			return floor(node.left, key);//如果小于,继续
		}
		Node t = floor(node.right, key);//不小于了,看看右边有没有
		if (t!=null) {//有就返回
			return t;
		}else {
			return node;//默认返回查找最下面的
		}
	}
	public Key ceiling(Key key) {//查找大于等于key的最大键
		Node x = ceiling(root, key);
		if (x == null) {
			return null;
		}
		return x.key;
	}
	private Node ceiling(Node node,Key key) {
		if (node == null) {
			return null;
		}//为空直接放
		int i = node.key.compareTo(key);//进行比对
		if (i==0) {
			return node;//相同直接返回
		}
		if (i > 0) {
			return floor(node.right, key);//如果大于,继续
		}
		Node t = floor(node.left, key);//不大于了,看看左边有没有
		if (t!=null) {//有就返回
			return t;
		}else {
			return node;//默认返回查找最下面的
		}
	}
	public Key max() {//查找最大键
		return max(root).key;
	}
	private Node max(Node node) {
		if (node.right == null) {
			return node;
		}
		return max(node.right);
	}
	public Key min() {//返回最小值
		return min(root).key;
	}
	private Node min(Node node) {
		if (node.left==null) {
			return node;
		}
		return min(node.left);
	}
	
	public int size() {//返回数量等同于返回根节点的子节点数量
		return size(root);
	}
	public int size(Node node) {
		if (node ==null) 
			return 0;
		else 
			return node.N;
	}
	public Comparable[] keys() {
		return keys(min(),max());
	}
	public Comparable[] keys(Key lo, Key hi) {
		Comparable[] asKey = new Comparable[size()];//创建了一个过大的数组
		keys(root, asKey,lo, hi);
		return asKey;
	}
	public void keys(Node node,Comparable[] a,Key lo,Key hi) {
		int count = 0;//设置一个辅助变量
		if(node == null) {
			return;//如果为空,直接返
		}
		int cmplo =lo.compareTo(node.key);//设置和开头比较的数
		int cmphi = hi.compareTo(node.key);//设置和结尾比较的数
		if (cmplo<0) {//只要比开头大,就递归向左
			keys(node.left, a, lo, hi);//这一步直接递归到了最右边
		}
		if (cmplo<=0&&cmphi>=0) {
			a[count++] = node.key;
		}
		if (cmphi>0) {//只要比开头小,就递归向右
			keys(node.right, a, lo, hi);
		}
	}
}
