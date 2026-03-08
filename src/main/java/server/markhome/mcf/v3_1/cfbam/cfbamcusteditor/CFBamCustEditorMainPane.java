// Description: Java 25 Custom JavaFX Schema.

/*
 *	server.markhome.mcf.CFBam
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFBam - Business Application Model
 *	
 *	This file is part of Mark's Code Fractal CFBam.
 *	
 *	Mark's Code Fractal CFBam is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFBam is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFBam is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU General Public License
 *	along with Mark's Code Fractal CFBam.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes,
 *	or integrate it with proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 *	
 */

package server.markhome.mcf.v3_1.cfbam.cfbamcusteditor;

import java.io.Serializable;
import java.math.*;
import java.text.*;
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.javafx.*;
import server.markhome.mcf.v3_1.cflib.javafx.CFReferenceEditor.ICFReferenceCallback;
import server.markhome.mcf.v3_1.cflib.xml.CFLibXmlUtil;
import server.markhome.mcf.v3_1.cfcore.msscf.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;
import server.markhome.mcf.v3_1.cfint.cfintobj.*;
import server.markhome.mcf.v3_1.cfbam.cfbamobj.*;
import server.markhome.mcf.v3_1.cfsec.cfsecsaxloader.*;
import server.markhome.mcf.v3_1.cfint.cfintsaxloader.*;
import server.markhome.mcf.v3_1.cfbam.cfbamsaxloader.*;
import server.markhome.mcf.v3_1.cfsec.cfsecjavafx.*;
import server.markhome.mcf.v3_1.cfint.cfintjavafx.*;
import server.markhome.mcf.v3_1.cfbam.cfbamjavafx.*;
import server.markhome.mcf.v3_1.cfbam.cfbamram.*;
import server.markhome.mcf.v3_1.cfbam.cfbammsscf.*;
import server.markhome.mcf.v3_1.cfbam.cfbamcustmssbamcf.*;
import server.markhome.mcf.v3_1.cfbam.cfbamcustxmlloader.*;
import server.markhome.mcf.v3_1.cfsec.cfseccustfx.*;
import server.markhome.mcf.v3_1.cfint.cfintcustfx.*;
import server.markhome.mcf.v3_1.cfbam.cfbamcustfx.*;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class CFBamCustEditorMainPane
extends AnchorPane
implements ICFSecAuthorizationCallbacks
{
	protected ICFBamCustEditorSchema customSchema = null;
	protected CFSplitPane splitPane = null;
	protected CFTitledPane appConsoleTitledPane = null;
	protected CFConsole appConsole = null;
	protected CFPaneFormManager paneFormManager = null;
	protected CFBamCustEditorHierarchyPane paneBamEditorHierarchy = null;
	protected ICFBamCustEditorSchema javafxSchema = null;
	protected String modelName = null;

	public class CFPaneFormManager
	extends CFBorderPane
	implements ICFFormManager
	{
		protected LinkedList<Node> formStack = new LinkedList<Node>();

		public CFPaneFormManager() {
			super();
		}

		/**
		 *	Sometimes you'll want to check to see what the current node
		 *	form being displayed is.
		 */
		public Node getCurrentForm() {
			if( formStack.isEmpty() ) {
				return( null );
			}
			Node retval = formStack.getLast();
			return( retval );
		}

		/**
		 *	Set the currently displayed form, clearing the form stack
		 *	and making the specified the root form for processing.
		 */
		public void setRootForm( Node value ) {
			final String S_ProcName = "setRootForm";
			if( value == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					1,
					"value" );
			}
			if( ! ( value instanceof ICFForm ) ) {
				throw new CFLibUnsupportedClassException( getClass(),
					S_ProcName,
					"Specified node value can not be cast to ICFForm" );
			}
			while( ! formStack.isEmpty() ) {
				Node top = formStack.getLast();
				ICFForm form = (ICFForm)top;
				try {
					form.forceCancelAndClose();
				}
				catch( RuntimeException e ) {
				}
				if( ! formStack.isEmpty() ) {
					if( top == formStack.getLast() ) {
						// Close wasn't called like it should have been
						formStack.removeLast();
					}
				}
			}
			formStack.add( value );
			setCenter( value );
		}

		/**
		 *	Push a form on the stack.  The specified form becomes the current
		 *	displayed form.  You should never push the same set of nodes
		 *	more than once to the form manager, and you should never push
		 *	a root form on the stack.
		 */
		public void pushForm( Node value ) {
			final String S_ProcName = "pushForm";
			if( value == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					1,
					"value" );
			}
			if( ! ( value instanceof ICFForm ) ) {
				throw new CFLibUnsupportedClassException( getClass(),
					S_ProcName,
					"Specified node value can not be cast to ICFForm" );
			}
			formStack.add( value );
			setCenter( value );
		}

		/**
		 *	Close the current form.  If you're paranoid, you can use
		 *	getCurrentForm() to make sure you're the current form before
		 *	invoking this method.
		 *
		 *	If the stack is empty and you are logged in, calling
		 *	closeCurrentForm() will cause showRootMainForm() to be
		 *	invoked.
		 */
		public void closeCurrentForm() {
			if( formStack.isEmpty() ) {
				return;
			}
			formStack.removeLast();
			if( formStack.isEmpty() ) {
				showRootMainForm();
			}
			else {
				setCenter( formStack.getLast() );
			}
		}

		/**
		 *	Show the root main form for the facet.
		 */
		public void showRootMainForm() {
			if( paneBamEditorHierarchy == null ) {
				paneBamEditorHierarchy = new CFBamCustEditorHierarchyPane( this, javafxSchema, modelName );
			}
			setRootForm( paneBamEditorHierarchy );
		}
	}

	public CFBamCustEditorMainPane(
		ICFBamCustEditorSchema argSchema,
		String argModelName )
	{
		super();
		final String S_ProcName = "construct";
		if( argModelName == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				2,
				"argModelName" );
		}
		modelName = argModelName;
		customSchema = argSchema;
		setMinHeight( 640 );
		setMinWidth( 1000 );
		splitPane = new CFSplitPane();

		appConsoleTitledPane = new CFTitledPane();
		appConsoleTitledPane.setText( "Console Log" );

		appConsole = new CFConsole();
		appConsole.setMinHeight( 60 );
		appConsoleTitledPane.setContent( appConsole );

		paneFormManager = new CFPaneFormManager();
		paneBamEditorHierarchy = new CFBamCustEditorHierarchyPane( paneFormManager, argSchema, modelName );
		paneFormManager.setRootForm( paneBamEditorHierarchy );
		splitPane.setOrientation( Orientation.VERTICAL );
		splitPane.getItems().add( paneFormManager );
		splitPane.getItems().add( appConsoleTitledPane );
		setTopAnchor( splitPane, 0.0 );
		setLeftAnchor( splitPane, 0.0 );
		setRightAnchor( splitPane, 0.0 );
		setBottomAnchor( splitPane, 0.0 );
		getChildren().addAll( splitPane );
	}

	public ICFBamCustEditorSchema getCustomSchema() {
		return( customSchema );
	}

	public void setCustomSchema( ICFBamCustEditorSchema argSchema ) {
		final String S_ProcName = "setCustomSchema";
		final String S_ArgNameSchema = "argSchema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				S_ArgNameSchema );
		}
		if( ! ( argSchema instanceof ICFBamCustEditorSchema ) ) {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				S_ArgNameSchema,
				argSchema,
				"ICFBamCustSchema" );
		}
		customSchema = (ICFBamCustEditorSchema)argSchema;
		if( paneBamEditorHierarchy != null ) {
			paneBamEditorHierarchy.setCustomSchema( customSchema );
		}
	}

	public void loggedIn() {
	}

	public void preLogout() {
	}
}
