package siaod1lab;

public class BST {
	class Node {
		int key;
		Node left, right;

		public Node(int data) {
			key = data;
			left = right = null;
		}
	}

	Node root;

	BST() {
		root = null;
	}

	void deleteKey(int key) {
		root = delete_Recursive(root, key);
	}

	Node delete_Recursive(Node root, int key) {
		if (root == null)
			return root;

		if (key < root.key)
			root.left = delete_Recursive(root.left, key);
		else if (key > root.key)
			root.right = delete_Recursive(root.right, key);
		else {
			// один ребенок
			if (root.left == null)
				return root.right;
			else if (root.right == null)
				return root.left;
			// два ребенка

			root.key = minValue(root.right);

			root.right = delete_Recursive(root.right, root.key);
		}
		return root;
	}

	// минимальный узел правого поддерева
	int minValue(Node root) {
		int minval = root.key;
		while (root.left != null) {
			minval = root.left.key;
			root = root.left;
		}
		return minval;
	}

	void insert(int key) {
		root = insert_Recursive(root, key);
	}

	Node insert_Recursive(Node root, int key) {
		if (root == null) {
			root = new Node(key);
			return root;
		}
		if (key < root.key)
			root.left = insert_Recursive(root.left, key);
		else if (key > root.key)
			root.right = insert_Recursive(root.right, key);
		return root;
	}

	/*
	 * Симметричный Обойти левое поддерево Посетить корень Обойти правое
	 * поддерево
	 * 
	 */
	void inorder() {
		inorder_Recursive(root);
	}

	void inorder_Recursive(Node root) {
		if (root != null) {
			inorder_Recursive(root.left);
			System.out.print(root.key + " ");
			inorder_Recursive(root.right);
		}
	}

	/*
	 * Прямой Посетить корень Обойти левое поддерево Обойти правое поддерево
	 */
	void preorder() {
		inorder_Recursive(root);
	}

	void preorder_Recursive(Node root) {
		if (root != null) {
			System.out.print(root.key + " ");
			inorder_Recursive(root.left);
			inorder_Recursive(root.right);
		}
	}

	/*
	 * В обратном порядке Обойти левое поддерево Обойти правое поддерево
	 * Посетить корень
	 */
	void postorder() {
		inorder_Recursive(root);
	}

	void postorder_Recursive(Node root) {
		if (root != null) {
			System.out.print(root.key + " ");
			inorder_Recursive(root.left);
			inorder_Recursive(root.right);
		}
	}

	boolean search(int key) {
		root = search_Recursive(root, key);
		if (root != null)
			return true;
		else
			return false;
	}

	Node search_Recursive(Node root, int key) {
		if (root == null || root.key == key)
			return root;
		if (root.key > key)
			return search_Recursive(root.left, key);
		return search_Recursive(root.right, key);
	}

	public static void main(String[] args) {
		BST bst = new BST();
		bst.insert(45);
		bst.insert(10);
		bst.insert(7);
		bst.insert(12);
		bst.insert(90);
		bst.insert(50);
		bst.insert(89);
		bst.insert(5);
		// симметричный
		System.out.println("Симметричный обход");
		bst.inorder();
		System.out.println("\n");
		bst.deleteKey(12);
		// прямой
		System.out.println("Прямой обход");
		bst.preorder();
		bst.deleteKey(5);
		System.out.println("\n");
		// обратный порядок
		System.out.println("Обратный обход");
		bst.postorder();
		boolean val = bst.search(7);
		System.out.println("\n");
		if (val == true) {
			System.out.println("Найден\n");
		} else {
			System.out.println("Не найден\n");
		}
		val = bst.search(100);
		if (val == true) {
			System.out.println("Найден\n");
		} else {
			System.out.println("Не найден\n");
		}
	}

}
