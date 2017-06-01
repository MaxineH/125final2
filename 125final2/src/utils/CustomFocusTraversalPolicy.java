package utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Vector;

public class CustomFocusTraversalPolicy extends FocusTraversalPolicy {

	Vector<Component> order;

	public CustomFocusTraversalPolicy(Vector<Component> order) {
		this.order=new Vector<Component>(order.size());
		this.order.addAll(order);
	}
	
	@Override
	public Component getComponentAfter(Container e, Component comp) {
		int index=(order.indexOf(comp)+1)%order.size();
		return order.get(index);
	}

	@Override
	public Component getComponentBefore(Container e, Component comp) {
		int index=order.indexOf(comp)-1;
		if (index<0) {
			index=order.size()-1;
		}
		return order.get(index);
	}

	@Override
	public Component getDefaultComponent(Container e) {
		return order.get(0);
	}

	@Override
	public Component getFirstComponent(Container e) {
		return order.get(0);
	}

	@Override
	public Component getLastComponent(Container e) {
		return order.lastElement();
	}
}