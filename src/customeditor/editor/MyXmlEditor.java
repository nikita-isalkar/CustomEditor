package customeditor.editor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import customeditor.providers.XMLContentProvider;
import customeditor.providers.XMLLabelProvider;

public class MyXmlEditor extends EditorPart {

	private TreeViewer viewer;
	private Group group ;
	private MessageBox messageBox;
	private List<Node> list = new ArrayList<Node>();
	private Document doc = null;

	@Override
	public void doSave(IProgressMonitor monitor) {

	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		
		messageBox = new MessageBox(parent.getShell(), SWT.ICON_WARNING | SWT.OK);
		messageBox.setText(" Warning");
		
		try {
			parseXML();
		} catch (SAXException e) {
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			return;
		}
		
		group = new Group(parent, SWT.NONE);
		group.setText("MyXML Editor");
		group.setLayout(new GridLayout(1, true));
		
		// Create the tree viewer to display the file tree
		viewer = new TreeViewer(group, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		viewer.setContentProvider(new XMLContentProvider());
		viewer.setLabelProvider(new XMLLabelProvider());
		viewer.getTree().setHeaderVisible(true);
		viewer.setInput(doc.getFirstChild().getParentNode());
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public boolean isDirty() {
		return false;
	}
	
	@SuppressWarnings("deprecation")
	private void parseXML() throws SAXException{
		IEditorInput inpt = super.getEditorInput();
		super.setTitle(inpt.getName());
		
		File f = Platform.getAdapterManager().getAdapter(inpt, File.class);
		System.out.println(f);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(f);
			doc.normalize();
			NodeList childNodes = doc.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				list.add(childNodes.item(i));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} 
	}

}
