package kr.co.manty;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

public class NewFeedPreviewWizardPage extends WizardPage{

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
		// TODO Auto-generated method stub
		
	}

}
