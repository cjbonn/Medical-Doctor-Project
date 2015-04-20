import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
public class JListTest extends JFrame
{
	JList list;

	public JListTest()
	{
		Object data[] = {new Info(1, "CJ"), new Info(2, "Brian")};
		list = new JList(data);
		add(list);
		list.addListSelectionListener(new ListListener());
		setSize(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);


	}

	public static void main(String[] args)
	{
		new JListTest();
	}

	private class ListListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			if(e.getValueIsAdjusting()) return;
			Info info = ((Info)list.getSelectedValue());
			System.out.println(info.id);
		}
	}
}

class Info
{
	String name;
	public int id;

	public Info(int _id, String _name)
	{
		name = _name;
		id = _id;
	}

	public String toString()
	{
		return name;
	}
}