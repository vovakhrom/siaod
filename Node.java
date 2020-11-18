public class Node {

	    public int data;                    //’ранимые данные (ключ)
	    public boolean leftIsThread;        //»стина, если ссылка на левое поддерево я¬Ћя≈“—я нитью
	    public Node left;                   //—сылка на левое поддерево
	    public Node right;                  //—сылка на правое поддерево

	    public Node(int data){              // онструтор дл€ инициализации
	        this.data = data;
	        leftIsThread = false;
	        left  = null;
	        right = null;
	    }

	    //ћетод дл€ вывода ключа
	    public void show() { System.out.print(data + " "); }
	
}
