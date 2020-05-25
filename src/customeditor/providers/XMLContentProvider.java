package customeditor.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLContentProvider implements ITreeContentProvider {

	@Override
	public boolean hasChildren(Object element) {
		Node node = (Node) element;
		return node.hasChildNodes();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		Node node = (Node)inputElement;
		List<Node> nodes = new ArrayList<Node>();
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			nodes.add(childNodes.item(i));
		}
		return nodes.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Node node = (Node) parentElement;
		List<Node> nodes = new ArrayList<Node>();
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node tmp = childNodes.item(i);
			System.out.println("node type: " + tmp.getNodeType());
			if(tmp.getNodeType() == Node.ELEMENT_NODE)
				nodes.add(tmp);
			else if (!isWhitespace(tmp.getNodeValue().trim()))
				nodes.add(tmp);
		}
		return nodes.toArray();
	}

	public boolean isWhitespace(String c) {
		return ("".equals(c) || "\t".equals(c) || "\n".equals(c) || "\r".equals(c));
	}
}
