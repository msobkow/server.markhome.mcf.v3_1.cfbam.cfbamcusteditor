// Description: Java 25 JavaFX Element TabPane implementation for Table.

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
 *	CFBamJavaFXTableEltTabPane JavaFX Element TabPane implementation
 *	for Table.
 */
public class CFBamCustEditorTableEltTabPane
extends CFTabPane
implements ICFBamJavaFXTablePaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFBamJavaFXSchema javafxSchema = null;
	protected boolean javafxIsInitializing = true;
	public final String LABEL_TabComponentsRelationList = "Optional Components Relation Definitions";
	protected CFTab tabComponentsRelation = null;
	public final String LABEL_TabComponentsIndexList = "Optional Components Index Definitions";
	protected CFTab tabComponentsIndex = null;
	public final String LABEL_TabComponentsColumnsList = "Optional Components Columns";
	protected CFTab tabComponentsColumns = null;
	public final String LABEL_TabComponentsChainsList = "Optional Components Chains";
	protected CFTab tabComponentsChains = null;
	public final String LABEL_TabComponentsDelDepList = "Optional Components Deletion Dependency";
	protected CFTab tabComponentsDelDep = null;
	public final String LABEL_TabComponentsClearDepList = "Optional Components Clear Relationships Dependency";
	protected CFTab tabComponentsClearDep = null;
	public final String LABEL_TabComponentsServerMethodsList = "Optional Components Server Methods";
	protected CFTab tabComponentsServerMethods = null;
	protected CFBorderPane tabViewComponentsRelationListPane = null;
	protected CFBorderPane tabViewComponentsIndexListPane = null;
	protected CFBorderPane tabViewComponentsColumnsListPane = null;
	protected CFBorderPane tabViewComponentsChainsListPane = null;
	protected CFBorderPane tabViewComponentsDelDepListPane = null;
	protected CFBorderPane tabViewComponentsClearDepListPane = null;
	protected CFBorderPane tabViewComponentsServerMethodsListPane = null;

	public CFBamCustEditorTableEltTabPane( ICFFormManager formManager, ICFBamJavaFXSchema argSchema, ICFBamTableObj argFocus ) {
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
		setJavaFXFocusAsTable( argFocus );
		// Wire the newly constructed Panes/Tabs to this TabPane
		tabComponentsRelation = new CFTab();
		tabComponentsRelation.setText( LABEL_TabComponentsRelationList );
		tabComponentsRelation.setContent( getTabViewComponentsRelationListPane() );
		getTabs().add( tabComponentsRelation );
		tabComponentsIndex = new CFTab();
		tabComponentsIndex.setText( LABEL_TabComponentsIndexList );
		tabComponentsIndex.setContent( getTabViewComponentsIndexListPane() );
		getTabs().add( tabComponentsIndex );
		tabComponentsColumns = new CFTab();
		tabComponentsColumns.setText( LABEL_TabComponentsColumnsList );
		tabComponentsColumns.setContent( getTabViewComponentsColumnsListPane() );
		getTabs().add( tabComponentsColumns );
		tabComponentsChains = new CFTab();
		tabComponentsChains.setText( LABEL_TabComponentsChainsList );
		tabComponentsChains.setContent( getTabViewComponentsChainsListPane() );
		getTabs().add( tabComponentsChains );
		tabComponentsDelDep = new CFTab();
		tabComponentsDelDep.setText( LABEL_TabComponentsDelDepList );
		tabComponentsDelDep.setContent( getTabViewComponentsDelDepListPane() );
		getTabs().add( tabComponentsDelDep );
		tabComponentsClearDep = new CFTab();
		tabComponentsClearDep.setText( LABEL_TabComponentsClearDepList );
		tabComponentsClearDep.setContent( getTabViewComponentsClearDepListPane() );
		getTabs().add( tabComponentsClearDep );
		tabComponentsServerMethods = new CFTab();
		tabComponentsServerMethods.setText( LABEL_TabComponentsServerMethodsList );
		tabComponentsServerMethods.setContent( getTabViewComponentsServerMethodsListPane() );
		getTabs().add( tabComponentsServerMethods );
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
		if( ( value == null ) || ( value instanceof ICFBamTableObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamTableObj" );
		}
	}

	public void setJavaFXFocusAsTable( ICFBamTableObj value ) {
		setJavaFXFocus( value );
	}

	public ICFBamTableObj getJavaFXFocusAsTable() {
		return( (ICFBamTableObj)getJavaFXFocus() );
	}

	protected class RefreshComponentsRelationList
	implements ICFRefreshCallback
	{
		public RefreshComponentsRelationList() {
		}

		public void refreshMe() {
			Collection<ICFBamRelationObj> dataCollection;
			ICFBamTableObj focus = (ICFBamTableObj)getJavaFXFocusAsTable();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsRelation( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsRelationListPane();
			ICFBamJavaFXRelationPaneList jpList = (ICFBamJavaFXRelationPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsRelationListPane() {
		if( tabViewComponentsRelationListPane == null ) {
			Collection<ICFBamRelationObj> dataCollection;
			ICFBamTableObj focus = (ICFBamTableObj)getJavaFXFocusAsTable();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsRelation( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFBamTableObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFBamTableObj ) ) {
				javafxContainer = (ICFBamTableObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsRelationListPane = javafxSchema.getRelationFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsRelationList(), false );
		}
		return( tabViewComponentsRelationListPane );
	}

	protected class RefreshComponentsIndexList
	implements ICFRefreshCallback
	{
		public RefreshComponentsIndexList() {
		}

		public void refreshMe() {
			Collection<ICFBamIndexObj> dataCollection;
			ICFBamTableObj focus = (ICFBamTableObj)getJavaFXFocusAsTable();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsIndex( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsIndexListPane();
			ICFBamJavaFXIndexPaneList jpList = (ICFBamJavaFXIndexPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsIndexListPane() {
		if( tabViewComponentsIndexListPane == null ) {
			Collection<ICFBamIndexObj> dataCollection;
			ICFBamTableObj focus = (ICFBamTableObj)getJavaFXFocusAsTable();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsIndex( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFBamTableObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFBamTableObj ) ) {
				javafxContainer = (ICFBamTableObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsIndexListPane = javafxSchema.getIndexFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsIndexList(), false );
		}
		return( tabViewComponentsIndexListPane );
	}

	protected class RefreshComponentsColumnsList
	implements ICFRefreshCallback
	{
		public RefreshComponentsColumnsList() {
		}

		public void refreshMe() {
			Collection<ICFBamValueObj> dataCollection;
			ICFBamTableObj focus = (ICFBamTableObj)getJavaFXFocusAsTable();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsColumns( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsColumnsListPane();
			ICFBamJavaFXValuePaneList jpList = (ICFBamJavaFXValuePaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsColumnsListPane() {
		if( tabViewComponentsColumnsListPane == null ) {
			Collection<ICFBamValueObj> dataCollection;
			ICFBamTableObj focus = (ICFBamTableObj)getJavaFXFocusAsTable();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsColumns( javafxIsInitializing );
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
			tabViewComponentsColumnsListPane = javafxSchema.getValueFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsColumnsList(), true );
		}
		return( tabViewComponentsColumnsListPane );
	}

	protected class RefreshComponentsChainsList
	implements ICFRefreshCallback
	{
		public RefreshComponentsChainsList() {
		}

		public void refreshMe() {
			Collection<ICFBamChainObj> dataCollection;
			ICFBamTableObj focus = (ICFBamTableObj)getJavaFXFocusAsTable();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsChains( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsChainsListPane();
			ICFBamJavaFXChainPaneList jpList = (ICFBamJavaFXChainPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsChainsListPane() {
		if( tabViewComponentsChainsListPane == null ) {
			Collection<ICFBamChainObj> dataCollection;
			ICFBamTableObj focus = (ICFBamTableObj)getJavaFXFocusAsTable();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsChains( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFBamTableObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFBamTableObj ) ) {
				javafxContainer = (ICFBamTableObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsChainsListPane = javafxSchema.getChainFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsChainsList(), false );
		}
		return( tabViewComponentsChainsListPane );
	}

	protected class RefreshComponentsDelDepList
	implements ICFRefreshCallback
	{
		public RefreshComponentsDelDepList() {
		}

		public void refreshMe() {
			Collection<ICFBamDelTopDepObj> dataCollection;
			ICFBamTableObj focus = (ICFBamTableObj)getJavaFXFocusAsTable();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsDelDep( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsDelDepListPane();
			ICFBamJavaFXDelTopDepPaneList jpList = (ICFBamJavaFXDelTopDepPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsDelDepListPane() {
		if( tabViewComponentsDelDepListPane == null ) {
			Collection<ICFBamDelTopDepObj> dataCollection;
			ICFBamTableObj focus = (ICFBamTableObj)getJavaFXFocusAsTable();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsDelDep( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFBamTableObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFBamTableObj ) ) {
				javafxContainer = (ICFBamTableObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsDelDepListPane = javafxSchema.getDelTopDepFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsDelDepList(), true );
		}
		return( tabViewComponentsDelDepListPane );
	}

	protected class RefreshComponentsClearDepList
	implements ICFRefreshCallback
	{
		public RefreshComponentsClearDepList() {
		}

		public void refreshMe() {
			Collection<ICFBamClearTopDepObj> dataCollection;
			ICFBamTableObj focus = (ICFBamTableObj)getJavaFXFocusAsTable();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsClearDep( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsClearDepListPane();
			ICFBamJavaFXClearTopDepPaneList jpList = (ICFBamJavaFXClearTopDepPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsClearDepListPane() {
		if( tabViewComponentsClearDepListPane == null ) {
			Collection<ICFBamClearTopDepObj> dataCollection;
			ICFBamTableObj focus = (ICFBamTableObj)getJavaFXFocusAsTable();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsClearDep( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFBamTableObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFBamTableObj ) ) {
				javafxContainer = (ICFBamTableObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsClearDepListPane = javafxSchema.getClearTopDepFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsClearDepList(), true );
		}
		return( tabViewComponentsClearDepListPane );
	}

	protected class RefreshComponentsServerMethodsList
	implements ICFRefreshCallback
	{
		public RefreshComponentsServerMethodsList() {
		}

		public void refreshMe() {
			Collection<ICFBamServerMethodObj> dataCollection;
			ICFBamTableObj focus = (ICFBamTableObj)getJavaFXFocusAsTable();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsServerMethods( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewComponentsServerMethodsListPane();
			ICFBamJavaFXServerMethodPaneList jpList = (ICFBamJavaFXServerMethodPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewComponentsServerMethodsListPane() {
		if( tabViewComponentsServerMethodsListPane == null ) {
			Collection<ICFBamServerMethodObj> dataCollection;
			ICFBamTableObj focus = (ICFBamTableObj)getJavaFXFocusAsTable();
			if( focus != null ) {
				dataCollection = focus.getOptionalComponentsServerMethods( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFBamTableObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFBamTableObj ) ) {
				javafxContainer = (ICFBamTableObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewComponentsServerMethodsListPane = javafxSchema.getServerMethodFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshComponentsServerMethodsList(), false );
		}
		return( tabViewComponentsServerMethodsListPane );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
		if( tabViewComponentsRelationListPane != null ) {
			((ICFBamJavaFXRelationPaneCommon)tabViewComponentsRelationListPane).setPaneMode( value );
		}
		if( tabViewComponentsIndexListPane != null ) {
			((ICFBamJavaFXIndexPaneCommon)tabViewComponentsIndexListPane).setPaneMode( value );
		}
		if( tabViewComponentsColumnsListPane != null ) {
			((ICFBamJavaFXValuePaneCommon)tabViewComponentsColumnsListPane).setPaneMode( value );
		}
		if( tabViewComponentsChainsListPane != null ) {
			((ICFBamJavaFXChainPaneCommon)tabViewComponentsChainsListPane).setPaneMode( value );
		}
		if( tabViewComponentsDelDepListPane != null ) {
			((ICFBamJavaFXDelTopDepPaneCommon)tabViewComponentsDelDepListPane).setPaneMode( value );
		}
		if( tabViewComponentsClearDepListPane != null ) {
			((ICFBamJavaFXClearTopDepPaneCommon)tabViewComponentsClearDepListPane).setPaneMode( value );
		}
		if( tabViewComponentsServerMethodsListPane != null ) {
			((ICFBamJavaFXServerMethodPaneCommon)tabViewComponentsServerMethodsListPane).setPaneMode( value );
		}
	}
}
