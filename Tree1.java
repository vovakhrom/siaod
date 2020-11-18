package siaod1lab;
import java.util.*;

import java.util.LinkedHashSet;
public class Tree1 {
    
	public static void main(String[] args) {
		BinTree tree = new BinTree();
		 Random rand = new Random();
		LinkedHashSet<Integer> set = new LinkedHashSet<Integer>();
		//������� 1000 ������ � ������ �� �� ���������
        for (int i = 0; i < 10; i++)
            set.add(rand.nextInt(100));
        //���������� �� ��������� � ������ ��� ������� ��������� � ������ ��������� ������
        System.out.println("������� ������� ��������� � �������� ������ ������: ");
        for (Integer it : set){
            System.out.print(it + " ");
            tree.insert(it);
        }
        System.out.println();

        //������������ �����
        tree.getInOrder();

        //������ ��������
        tree.makeSymmetricallyThreaded();

        //������� ����� ��������
        tree.threadedInOrderTraverse();
        tree.threadedInOrderTraverseReverse();

        //������� ������ � ��������
        tree.insertToSymmetricTree(-1);
        tree.insertToSymmetricTree(-52);
        tree.insertToSymmetricTree(-250);
        tree.insertToSymmetricTree(120);
        tree.insertToSymmetricTree(170);
        tree.insertToSymmetricTree(130);
        tree.insertToSymmetricTree(150);

        System.out.println("����� ������� ����� ������:");
        tree.threadedInOrderTraverse();

        //������� ������� �������������� �����
        tree.deleteFromSymmetricTree(-998);
        tree.deleteFromSymmetricTree(998);

        //������� ������ �������� ����, ��� ��������
        int N = set.size() / 2;
        for (Integer it : set){
            tree.deleteFromSymmetricTree(it);
            N--;
            if (N == 0) break;
        }

        //����� ��������
        System.out.println("����� ��������:");
        tree.threadedInOrderTraverse();
	}

}
