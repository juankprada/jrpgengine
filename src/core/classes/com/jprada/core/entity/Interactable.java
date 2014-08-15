package com.jprada.core.entity;

import com.jprada.core.entity.MapObject.Direction;
import com.jprada.core.entity.utils.InteractBox;

public interface Interactable {

	/**
	 * Method to execute interaction. A boolean must be returned to know if interaction
	 * has finished yet.
	 * @param other The object to which it is interacting
	 * @return true if interaction finished or false otherwise
	 */
	public boolean onInteract(Interactable other);
	
	public InteractBox getInteractBox();
	
	public void setFacingDirection(Direction direction);
}
