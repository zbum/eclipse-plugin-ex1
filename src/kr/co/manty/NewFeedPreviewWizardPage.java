package kr.co.manty;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class NewFeedPreviewWizardPage extends WizardPage{
	private Browser browser;

	protected NewFeedPreviewWizardPage() {
		super("NewFeedPreviewWizardPage");
		setTitle("Preview of Feed");
		setMessage("A preview of the provided URL is shown below");
		setImageDescriptor(
				ImageDescriptor.createFromFile(NewFeedPreviewWizardPage.class, "/icons/full/wizban/newfeed_wiz.png")
				);
		
	}

	@Override
	public void createControl(Composite parent) {
		Composite page = new Composite(parent, SWT.NONE);
		setControl(page);
		page.setLayout(new FillLayout());
		browser = new Browser(page, SWT.NONE);
		browser.setText("loading..");
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (visible && browser!= null && !browser.isDisposed()) {
			NewFeedWizardPage newFeedWizardPage = (NewFeedWizardPage)getWizard().getPage("NewFeedWizardPage");
			String url = newFeedWizardPage.getUrl();
			browser.setUrl(url);
		}
		super.setVisible(visible);
	}

}
