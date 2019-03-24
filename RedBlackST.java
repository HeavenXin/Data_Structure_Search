package RedBlackST;

public class RedBlackST<Key extends Comparable<Key>, Value> {
	private Node root;// 根节点
	private static final boolean Red = true;
	private static final boolean Black = false;// 设置红色

	private class Node {
		Key key;
		Value value;
		Node left, right;// 左右子节点\
		int N;// 子树中的结点
		Boolean color;// 红黑

		Node(Key key, Value value, int N, Boolean color) {// 构造方法
			this.key = key;
			this.value = value;
			this.N = N;
			this.color = color;
		}
	}

	/**
	 * 
	 * @author Administrator
	 * 
	 *         如果右子节点是红色而左子节点为黑色的,进行左旋转 如果左子节点为红色的且它的左子节点也是红色,进行右旋转
	 *         左右子节点皆为红色的,进行颜色旋转 基本的插入顺序
	 * 
	 */
	public void put(Key key, Value val) {// 插入
		root = put(root, key, val);
		root.color = Black;// 设置为黑色
	}

	private Node put(Node node, Key key, Value val) {
		if (node == null) {// 如果为空
			return new Node(key, val, 1, Red);// 返回一个新Node
		}
		// 仍然采用递归的方式
		int tmp = node.key.compareTo(key);// 进行比较
		if (tmp > 0) {
			node.left = put(node.left, key, val);
		} else if (tmp < 0) {
			node.right = put(node.right, key, val);// 递归向下
		} else
			node.value = val; // 相同更新
		if (isRed(node.right) && !(isRed(node.left))) {// 如果右子节点是红色而左子节点为黑色的
			node = rotataLeft(node);// 进行左旋转
		}
		if (isRed(node.left) && isRed(node.left.left)) {// 如果左子节点为红色的且它的左子节点也是红色
			node = rotataRight(node);// ,进行右旋转
		}
		if (isRed(node.left) && isRed(node.right)) {// 左右子节点皆为红色的
			filpColors(node);
		} // 这三次方式让其进行向上传递
		node.N = size(node.left) + size(node.right) + 1;// 数量+1
		return node;
	}

	public Node moveRedLeft(Node node) {// 负责处理2结点
		filpColors(node);// 从父节点中借一个
		if (isRed(node.right.left)) {// 如果为红,说明兄弟结点不是一个2结点
			node.right = rotataRight(node.right);
			node = rotataLeft(node);
			filpColors(node);// 从兄弟节点借了不需要了,借了之后再返还回去
		}
		return node;
	}

	public void deleteMin() {// 删除最小值
		if (!isRed(root.left) && !isRed(root.right)) {
			root.color = Red;// 如果左右子节点都是-2结点,可以将根为红结点
		}
		root = deleteMin(root);
		root.color = Black;// 借完以后，我们将根节点的颜色复原
	}

	private Node deleteMin(Node x) {
		if (x.left == null)
			return null;
		if (!isRed(x.left) && !isRed(x.left.left)) // 判断x的左节点是不是2-节点
			x = moveRedLeft(x);
		x.left = deleteMin(x.left);
		return balance(x); // 解除临时组成的4-节点
	}

	private Node moveRedRight(Node node) {
		filpColors(node);
		if (isRed(node.left.left)) {// 在这里对于兄弟节点的判断都是.left，因为红色节点只会出现在左边
			node = rotataRight(node);
			filpColors(node);
		}
		return node;
	}

	public void deleteMax() {// 删除最大值
		if (!isRed(root.left) && !isRed(root.right)) {
			root.color = Red;// 如果左右子节点都是-2结点,可以将根为红结点
		}
		root = deleteMax(root);
		root.color = Black;// 借完以后，我们将根节点的颜色复原
	}

	private Node deleteMax(Node x) {
		if (isRed(x.left)) {
			// 右子节点从父节点中获得节点的时候，我们需要将左边节点给于到右边节点，如果我们不移动的话，会破坏树的平衡
			x = rotataRight(x);
		}
		if (x.right == null)
			return null;
		if (!isRed(x.right) && !isRed(x.right.left)) // 判断x的左节点是不是2-节点
			x = moveRedLeft(x);
		x.right = deleteMin(x.right);
		return balance(x); // 解除临时组成的4-节点
	}

	public void delete(Key key) {
		if (!isRed(root.left) && !isRed(root.right)) {
			root.color = Red;
		}
		root = delete(root, key);
		root.color = Black;
	}

	private Node delete(Node h, Key key) {
		int tmp = key.compareTo(h.key);
		if (tmp<0) { // 当目标键小于当前键的时候，我们做类似于寻找最小的操作，向树左边移动，合并父子结点来消除2-结点
			if (h.left == null) {
				return null;
			}//为空直接返回
			if (isRed(h.left) && !isRed(h.left.left)) {//是-2结点
				h = moveRedLeft(h);//开借
			}
			h.left = delete(h.left, key);//递归
		} else { // 当目标键大于当前键的时候，我们向右移动，并做与deleteMax相同的操作，
			//如果相同的话，我们使用和二叉树的删除一样的操作，获取当前键的右子树的最小健，然后交换，并将目标键删除
			if (isRed(h.left)) {
				h = rotataRight(h);
			}
			if (key != h.key && h.right == null) { // 我们没有找到目标键，我们删除
				return null;
			}
			if (!isRed(h.right) && isRed(h.right.left)) {
				h = moveRedRight(h);
			}
			if (key == h.key) {//如果相同
				h.value = get(h.right, min(h.right).key);//与其后驱交换
				h.key = min(h.right).key;
				h.right = deleteMin(h.right);//删除后驱
			} else
				h.right = delete(h.right, key);//都不满足,继续查找啊
		}
		return balance(h);
	}

	private Node balance(Node h) {
		if (isRed(h.right))
			h = rotataLeft(h);//毫无必要,但是为了保险还是加上去了,因为左边不太可能有了,作为先驱删掉了
		if (isRed(h.right) && !isRed(h.left))
			h = rotataLeft(h);
		if (isRed(h.left) && isRed(h.left.left))
			h = rotataRight(h);
		if (isRed(h.left) && isRed(h.right))
			filpColors(h);
		h.N = size(h.left) + size(h.right) + 1;
		return h;
	}

	// 记下来三个方法维持了红黑树的稳定性
	private Node rotataLeft(Node node) {// 接到右边结点上
		Node chan = node.right;
		node.right = chan.left;
		chan.left = node; // 交换基本完成
		chan.color = node.color;
		node.color = Red;// 设置颜色
		chan.N = node.N;
		node.N = 1 + size(node.left) + size(node.right);
		return chan;// 返回交换完结点
	}

	private Node rotataRight(Node node) {// 接到左边结点上
		Node chan = node.left;
		node.left = chan.right;
		chan.right = node;// 交换基本完成
		chan.color = node.color;
		node.color = Red;// 设置颜色
		chan.N = node.N;
		node.N = 1 + size(node.left) + size(node.left);
		return chan;// 返回交换完结点
	}

	private void filpColors(Node node) {// 在进行插入之后,转换为一种固定格式,在把固定格式渲染
		node.color = Red;
		node.right.color = Black;
		node.left.color = Black;
	}

	public int size() {// 返回数量等同于返回根节点的子节点数量
		return size(root);
	}

	public int size(Node node) {// 获取数值
		if (node == null)
			return 0;
		else
			return node.N;
	}

	private boolean isRed(Node node) {
		return node.color == Red;// 是否为红
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
}
