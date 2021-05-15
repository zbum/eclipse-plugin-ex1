package kr.co.manty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class NewFeedWizard extends Wizard implements INewWizard {
	
	public static final String FEEDS_FILE = "news.feeds";
	public static final String FEEDS_PROJECT = "bookmarks";
	
	private NewFeedWizardPage newFeedWizardPage = new NewFeedWizardPage();
	private NewFeedPreviewWizardPage newFeedPreviewWizardPage = new NewFeedPreviewWizardPage();

	@Override
	public boolean performFinish() {
		String url = newFeedWizardPage.getUrl();
		String description = newFeedWizardPage.getDescriptionText();
		
		try {
			boolean fork = false;
			boolean cancel = false;
			getContainer().run(fork, cancel, m ->{
				try {
					if (url != null && description !=null) {
						addFeed(url, description, m);
					}
				}catch(Exception e){
					throw new InvocationTargetException(e);
				}
			});
			
			return true;
		}catch(InvocationTargetException e) {
			newFeedWizardPage.setMessage(e.getTargetException().toString(), IMessageProvider.ERROR);
			return false;
		}catch(InterruptedException e) {
			return true;
		}
	}
	
	@Override
	public void addPages() {
		addPage(newFeedWizardPage);
		addPage(newFeedPreviewWizardPage);
		setHelpAvailable(true);
		setNeedsProgressMonitor(true);
	}
	
	private IFile getFile(String project, String name, IProgressMonitor monitor) throws CoreException{
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject bookmarks = workspace.getRoot().getProject(project);
		if (!bookmarks.exists()) {
			bookmarks.create(monitor);
		}
		if (!bookmarks.isOpen()) {
			bookmarks.open(monitor);
		}
		return bookmarks.getFile(name);
	}
	
	private synchronized void addFeed(String url, String description, IProgressMonitor monitor) throws CoreException, IOException {
		SubMonitor subMonitor = SubMonitor.convert(monitor,2);
		if(subMonitor.isCanceled()) return;
		
		Properties feeds = new Properties();
		IFile file = getFile(FEEDS_PROJECT, FEEDS_FILE, null);
		if (file.exists()) {
			feeds.load(file.getContents());
		}
		feeds.setProperty(url, description);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		feeds.store(baos, null);
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		if (file.exists()) {
			file.setContents(bais, true, false, null);
		}else {
			file.create(bais, true, null);
		}
		subMonitor.worked(1);
		if(monitor != null) {
			monitor.done();
		}
	}


	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}
	
	


}
