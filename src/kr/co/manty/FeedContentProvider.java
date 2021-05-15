package kr.co.manty;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class FeedContentProvider implements ITreeContentProvider{
	private static final Object[] NO_CHILDREN = new Object[0];

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Object[] result = NO_CHILDREN;
		if (parentElement instanceof IResource) {
			IResource resource = (IResource)parentElement;
			if(resource.getName().endsWith(".feeds")) {
				try {
					Properties properties = new Properties();
					InputStream stream = resource.getLocationURI().toURL().openStream();
				    properties.load(stream);
				    stream.close();
				    result = properties.entrySet().stream()
				    .map(it -> new Feed((String)it.getValue(), (String)it.getKey()))
				    .collect(Collectors.toList()).toArray(new Feed[0]);
				    		
				}catch(Exception e) {
					return NO_CHILDREN;
				}
			}
		}
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

}
