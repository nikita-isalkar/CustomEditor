package customeditor.providers;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.w3c.dom.Node;

public class XMLLabelProvider implements ILabelProvider {

	@Override
	public String getText(Object element) {
		Node node = (Node) element;
		System.out.println("node-type:" + node.getNodeType() + "Node Name :" + node.getNodeName()
				+ "Node Value :" + node.getNodeValue() + "Node text :" + node.getTextContent());
		if (node.getNodeType() == 3)
			return node.getNodeValue();
		return node.getNodeName();
	}

	@Override
	public void addListener(ILabelProviderListener listener) {

	}

	@Override
	public void dispose() {
		//Nothing to dispose
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {

	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

}
