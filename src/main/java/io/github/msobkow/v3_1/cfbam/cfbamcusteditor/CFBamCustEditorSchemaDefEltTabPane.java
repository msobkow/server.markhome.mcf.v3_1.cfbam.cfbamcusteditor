// Description: Java 25 JavaFX Element TabPane implementation for SchemaDef.

/*
 *	io.github.msobkow.CFBam
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

package io.github.msobkow.v3_1.cfbam.cfbamcusteditor;

import java.io.Serializable;
import java.math.*;
import java.text.*;
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import io.github.msobkow.v3_1.cflib.*;
import io.github.msobkow.v3_1.cflib.dbutil.*;
import io.github.msobkow.v3_1.cflib.javafx.*;
import io.github.msobkow.v3_1.cflib.javafx.CFReferenceEditor.ICFReferenceCallback;
import io.github.msobkow.v3_1.cflib.xml.CFLibXmlUtil;
import io.github.msobkow.v3_1.cfcore.msscf.*;
import io.github.msobkow.v3_1.cfsec.cfsec.*;
import io.github.msobkow.v3_1.cfint.cfint.*;
import io.github.msobkow.v3_1.cfbam.cfbam.*;
import io.github.msobkow.v3_1.cfsec.cfsecobj.*;
import io.github.msobkow.v3_1.cfint.cfintobj.*;
import io.github.msobkow.v3_1.cfbam.cfbamobj.*;
import io.github.msobkow.v3_1.cfsec.cfsecsaxloader.*;
import io.github.msobkow.v3_1.cfint.cfintsaxloader.*;
import io.github.msobkow.v3_1.cfbam.cfbamsaxloader.*;
import io.github.msobkow.v3_1.cfsec.cfsecjavafx.*;
import io.github.msobkow.v3_1.cfint.cfintjavafx.*;
import io.github.msobkow.v3_1.cfbam.cfbamjavafx.*;
import io.github.msobkow.v3_1.cfbam.cfbamram.*;
import io.github.msobkow.v3_1.cfbam.cfbammsscf.*;
import io.github.msobkow.v3_1.cfbam.cfbamcustmssbamcf.*;
import io.github.msobkow.v3_1.cfbam.cfbamcustxmlloader.*;
import io.github.msobkow.v3_1.cfsec.cfseccustfx.*;
import io.github.msobkow.v3_1.cfint.cfintcustfx.*;
import io.github.msobkow.v3_1.cfbam.cfbamcustfx.*;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

/**
 *	CFBamJavaFXSchemaDefEltTabPane JavaFX Element TabPane implementation
 *	for SchemaDef.
 */
public class CFBamCustEditorSchemaDefEltTabPane
extends CFTabPane
implements ICFBamJavaFXSchemaDefPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFBamJavaFXSchema javafxSchema = null;
	protected boolean javafxIsInitializing = true;
	public final String LABEL_TabComponentsTablesList = "Optional Components Table Definitions";
	protected CFTab tabComponentsTables = null;
	public final String LABEL_TabComponentsTypesList = "Optional Components Type Definitions";
	protected CFTab tabComponentsTypes = null;
	public final String LABEL_TabComponentsSchemaRefsList = "Optional Components Schema Refs";
	protected CFTab tabComponentsSchemaRefs = null;
	protected CFBorderPane tabViewComponentsTablesListPane = null;
	protected CFBorderPane tabViewComponentsTypesListPane = null;
	protected CFBorderPane tabViewComponentsSchemaRefsListPane = null;

	public CFBamCustEditorSchemaDefEltTabPane( ICFFormManager formManager, ICFBamJavaFXSchema argSchema, ICFBamSchemaDefObj argFocus ) {
		super();
		final String S_ProcName = "construct-schema-focus";
		if( formManager == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"formManager" );
		}
		cfFormManager = formManager;
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				2,
				"argSchema" );
		}
		// argFocus is optional; focus may be set later during execution as
		// conditions of the runtime change.
		javafxSchema = argSchema;
		setJavaFXFocusAsSchemaDef( argFocus );
		// Wire the newly constructed Panes/Tabs to this TabPane
		tabComponentsTables = new CFTab();
		tabComponentsTables.setText( LABEL_TabComponentsTablesList );
		tabComponentsTables.setContent( getTabViewComponentsTablesListPane() );
		getTabs().add( tabComponentsTables );
		tabComponentsTypes = new CFTab();
		tabComponentsTypes.setText( LABEL_TabComponentsTypesList );
		tabComponentsTypes.setContent( getTabViewComponentsTypesListPane() );
		getTabs().add( tabComponentsTypes );
		tabComponentsSchemaRefs = new CFTab();
		tabComponentsSchemaRefs.setText( LABEL_TabComponentsSchemaRefsList );
		tabComponentsSchemaRefs.setContent( getTabViewComponentsSchemaRefsListPane() );
		getTabs().add( tabComponentsSchemaRefs );
		javafxIsInitializing = false;
	}

	public ICFFormManager getCFFormManager() {
		return( cfFormManager );
	}

	public void setCFFormManager( ICFFormManager value ) {
		final String S_ProcName = "setCFFormManager";
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"value" );
		}
		cfFormManager = value;
	}

	public ICFBamJavaFXSchema getJavaFXSchema() {
		return( javafxSchema );
	}

	public void setJavaFXFocus( ICFLibAnyObj value ) {
		final String S_ProcName = "setJavaFXFocus";
		if( ( value == null ) || ( value instanceof ICFBamSchemaDefObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamSchemaDefObj" );
		}
	}

	public void setJavaFXFocusAsSchemaDef( ICFBamSchemaDefObj value ) {
		setJavaFXFocus( value );
	}

	public ICFBamSchemaDefObj getJavaFXFocusAsSchemaDef() {
		return( (ICFBamSchemaDefObj)getJavaFXFocus() );
	}

	protected class RefreshComponentsTablesList
	implements ICFRefreshCallback
	{
		public RefreshComponentsTablesList() {
		}

		public void refreshMe() {
			Collection<ICFBamTableObj> dataCollection;
			ICFBamSchemaDefObj focus = (ICFBamSchemaDefObj)getJavaFXFocusAsSchemaDef();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsTables( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsTablesListPane();
			ICFBamJavaFXTablePaneList jpList = (ICFBamJavaFXTablePaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsTablesListPane() {
		if( tabViewComponentsTablesListPane == null ) {
			Collection<ICFBamTableObj> dataCollection;
			ICFBamSchemaDefObj focus = (ICFBamSchemaDefObj)getJavaFXFocusAsSchemaDef();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsTables( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFBamSchemaDefObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFBamSchemaDefObj ) ) {
				javafxContainer = (ICFBamSchemaDefObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsTablesListPane = javafxSchema.getTableFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsTablesList(), false );
		}
		return( tabViewComponentsTablesListPane );
	}

	protected class RefreshComponentsTypesList
	implements ICFRefreshCallback
	{
		public RefreshComponentsTypesList() {
		}

		public void refreshMe() {
			Collection<ICFBamValueObj> dataCollection;
			ICFBamSchemaDefObj focus = (ICFBamSchemaDefObj)getJavaFXFocusAsSchemaDef();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsTypes( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsTypesListPane();
			ICFBamJavaFXValuePaneList jpList = (ICFBamJavaFXValuePaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsTypesListPane() {
		if( tabViewComponentsTypesListPane == null ) {
			Collection<ICFBamValueObj> dataCollection;
			ICFBamSchemaDefObj focus = (ICFBamSchemaDefObj)getJavaFXFocusAsSchemaDef();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsTypes( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFBamScopeObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFBamScopeObj ) ) {
				javafxContainer = (ICFBamScopeObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsTypesListPane = javafxSchema.getValueFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsTypesList(), false );
		}
		return( tabViewComponentsTypesListPane );
	}

	protected class RefreshComponentsSchemaRefsList
	implements ICFRefreshCallback
	{
		public RefreshComponentsSchemaRefsList() {
		}

		public void refreshMe() {
			Collection<ICFBamSchemaRefObj> dataCollection;
			ICFBamSchemaDefObj focus = (ICFBamSchemaDefObj)getJavaFXFocusAsSchemaDef();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsSchemaRefs( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsSchemaRefsListPane();
			ICFBamJavaFXSchemaRefPaneList jpList = (ICFBamJavaFXSchemaRefPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );

			refreshTableComponents();
			refreshTypeComponents();
		}

		public void refreshTableComponents() {
			Collection<ICFBamTableObj> dataCollection;
			ICFBamSchemaDefObj focus = (ICFBamSchemaDefObj)getJavaFXFocusAsSchemaDef();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsTables( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsTablesListPane();
			ICFBamJavaFXTablePaneList jpList = (ICFBamJavaFXTablePaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}

		public void refreshTypeComponents() {
			Collection<ICFBamValueObj> dataCollection;
			ICFBamSchemaDefObj focus = (ICFBamSchemaDefObj)getJavaFXFocusAsSchemaDef();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsTypes( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsTypesListPane();
			ICFBamJavaFXValuePaneList jpList = (ICFBamJavaFXValuePaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsSchemaRefsListPane() {
		if( tabViewComponentsSchemaRefsListPane == null ) {
			Collection<ICFBamSchemaRefObj> dataCollection;
			ICFBamSchemaDefObj focus = (ICFBamSchemaDefObj)getJavaFXFocusAsSchemaDef();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsSchemaRefs( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFBamSchemaDefObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFBamSchemaDefObj ) ) {
				javafxContainer = (ICFBamSchemaDefObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsSchemaRefsListPane = javafxSchema.getSchemaRefFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsSchemaRefsList(), true );
		}
		return( tabViewComponentsSchemaRefsListPane );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
		if( tabViewComponentsTablesListPane != null ) {
			((ICFBamJavaFXTablePaneCommon)tabViewComponentsTablesListPane).setPaneMode( value );
		}
		if( tabViewComponentsTypesListPane != null ) {
			((ICFBamJavaFXValuePaneCommon)tabViewComponentsTypesListPane).setPaneMode( value );
		}
		if( tabViewComponentsSchemaRefsListPane != null ) {
			((ICFBamJavaFXSchemaRefPaneCommon)tabViewComponentsSchemaRefsListPane).setPaneMode( value );
		}
	}
}
