// Description: Java 25 JavaFX List of Obj Pane implementation for Chain.

/*
 *	Mark's Code Fractal 3.1 CFBam - Business Application Model
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *
 *	These files are part of Mark's Code Fractal CFBam.
 *
 *	Mark's Code Fractal CFBam is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU General Public License,
 *	Version 3 or later with classpath and static linking exceptions.
 *
 *	As a special exception, Mark Sobkow gives you permission to link this library
 *	with independent modules to produce an executable, provided that none of them
 *	conflict with the intent of the GPLv3; that is, you are not allowed to invoke
 *	the methods of this library from non-GPLv3-compatibly licensed code. You may not
 *	implement an LPGLv3 "wedge" to try to bypass this restriction. That said, code which
 *	does not rely on this library is free to specify whatever license its authors decide
 *	to use. Mark Sobkow specifically rejects the infectious nature of the GPLv3, and
 *	considers the mere act of including GPLv3 modules in an executable to be perfectly
 *	reasonable given tools like modern Java's single-jar deployment options.
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
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *	CFBamJavaFXChainListPane JavaFX List of Obj Pane implementation
 *	for Chain.
 */
public class CFBamCustEditorChainListPane
extends CFBorderPane
implements ICFBamJavaFXChainPaneList
{
	public static String S_FormName = "List Chain";
	protected ICFBamJavaFXSchema javafxSchema = null;
	protected Collection<ICFBamChainObj> javafxDataCollection = null;
	protected ObservableList<ICFBamChainObj> observableListOfChain = null;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected CFButton buttonAddChain = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected TableView<ICFBamChainObj> dataTable = null;
	protected TableColumn<ICFBamChainObj, Long> tableColumnId = null;
	protected TableColumn<ICFBamChainObj, String> tableColumnName = null;
	protected TableColumn<ICFBamChainObj, String> tableColumnShortName = null;
	protected TableColumn<ICFBamChainObj, String> tableColumnLabel = null;
	protected TableColumn<ICFBamChainObj, String> tableColumnShortDescription = null;
	protected TableColumn<ICFBamChainObj, String> tableColumnDescription = null;
	protected TableColumn<ICFBamChainObj, String> tableColumnSuffix = null;
	protected TableColumn<ICFBamChainObj, ICFBamSchemaDefObj> tableColumnLookupDefSchema = null;
	protected TableColumn<ICFBamChainObj, ICFBamRelationObj> tableColumnLookupPrevRel = null;
	protected TableColumn<ICFBamChainObj, ICFBamRelationObj> tableColumnLookupNextRel = null;

	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected boolean javafxIsInitializing = true;
	protected boolean javafxSortByChain = false;
	protected ICFBamTableObj javafxContainer = null;
	protected ICFRefreshCallback javafxRefreshCallback = null;
	class ViewEditClosedCallback implements ICFFormClosedCallback {
		public ViewEditClosedCallback() {
		}

		@Override
		public void formClosed( ICFLibAnyObj affectedObject ) {
			if( affectedObject != null ) {
				refreshMe();
			}
		}
	}

	protected ViewEditClosedCallback viewEditClosedCallback = null;

	public ICFFormClosedCallback getViewEditClosedCallback() {
		if( viewEditClosedCallback == null ) {
			viewEditClosedCallback = new ViewEditClosedCallback();
		}
		return( viewEditClosedCallback );
	}

	class DeleteCallback implements ICFDeleteCallback {
		public DeleteCallback() {
		}

		@Override
		public void deleted( ICFLibAnyObj deletedObject ) {
			if( deletedObject != null ) {
				refreshMe();
			}
		}

		@Override
		public void formClosed( ICFLibAnyObj affectedObject ) {
			if( affectedObject != null ) {
				refreshMe();
			}
		}
	}

	protected DeleteCallback deleteCallback = null;

	public ICFDeleteCallback getDeleteCallback() {
		if( deleteCallback == null ) {
			deleteCallback = new DeleteCallback();
		}
		return( deleteCallback );
	}


	public CFBamCustEditorChainListPane( ICFFormManager formManager,
		ICFBamJavaFXSchema argSchema,
		ICFBamTableObj argContainer,
		ICFBamChainObj argFocus,
		Collection<ICFBamChainObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
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
		javaFXFocus = argFocus;
		javafxContainer = argContainer;
		javafxRefreshCallback = refreshCallback;
		javafxSortByChain = sortByChain;
		setJavaFXDataCollection( argDataCollection );
		dataTable = new TableView<ICFBamChainObj>();
		tableColumnId = new TableColumn<ICFBamChainObj,Long>( "Id" );
		tableColumnId.setCellValueFactory( new Callback<CellDataFeatures<ICFBamChainObj,Long>,ObservableValue<Long> >() {
			public ObservableValue<Long> call( CellDataFeatures<ICFBamChainObj, Long> p ) {
				ICFBamChainObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					long value = obj.getRequiredId();
					Long wrapped = Long.valueOf( value );
					ReadOnlyObjectWrapper<Long> observable = new ReadOnlyObjectWrapper<Long>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnId.setCellFactory( new Callback<TableColumn<ICFBamChainObj,Long>,TableCell<ICFBamChainObj,Long>>() {
			@Override public TableCell<ICFBamChainObj,Long> call(
				TableColumn<ICFBamChainObj,Long> arg)
			{
				return new CFInt64TableCell<ICFBamChainObj>();
			}
		});
		dataTable.getColumns().add( tableColumnId );
		tableColumnName = new TableColumn<ICFBamChainObj,String>( "Name" );
		tableColumnName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamChainObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamChainObj, String> p ) {
				ICFBamChainObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnName.setCellFactory( new Callback<TableColumn<ICFBamChainObj,String>,TableCell<ICFBamChainObj,String>>() {
			@Override public TableCell<ICFBamChainObj,String> call(
				TableColumn<ICFBamChainObj,String> arg)
			{
				return new CFStringTableCell<ICFBamChainObj>();
			}
		});
		dataTable.getColumns().add( tableColumnName );
		tableColumnLookupDefSchema = new TableColumn<ICFBamChainObj, ICFBamSchemaDefObj>( "Defining Schema" );
		tableColumnLookupDefSchema.setCellValueFactory( new Callback<CellDataFeatures<ICFBamChainObj,ICFBamSchemaDefObj>,ObservableValue<ICFBamSchemaDefObj> >() {
			public ObservableValue<ICFBamSchemaDefObj> call( CellDataFeatures<ICFBamChainObj, ICFBamSchemaDefObj> p ) {
				ICFBamChainObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamSchemaDefObj ref = obj.getOptionalLookupDefSchema();
					ReadOnlyObjectWrapper<ICFBamSchemaDefObj> observable = new ReadOnlyObjectWrapper<ICFBamSchemaDefObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupDefSchema.setCellFactory( new Callback<TableColumn<ICFBamChainObj,ICFBamSchemaDefObj>,TableCell<ICFBamChainObj,ICFBamSchemaDefObj>>() {
			@Override public TableCell<ICFBamChainObj,ICFBamSchemaDefObj> call(
				TableColumn<ICFBamChainObj,ICFBamSchemaDefObj> arg)
			{
				return new CFReferenceTableCell<ICFBamChainObj,ICFBamSchemaDefObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupDefSchema );
		tableColumnShortName = new TableColumn<ICFBamChainObj,String>( "Short Name" );
		tableColumnShortName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamChainObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamChainObj, String> p ) {
				ICFBamChainObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalShortName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnShortName.setCellFactory( new Callback<TableColumn<ICFBamChainObj,String>,TableCell<ICFBamChainObj,String>>() {
			@Override public TableCell<ICFBamChainObj,String> call(
				TableColumn<ICFBamChainObj,String> arg)
			{
				return new CFStringTableCell<ICFBamChainObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortName );
		tableColumnDescription = new TableColumn<ICFBamChainObj,String>( "Description" );
		tableColumnDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamChainObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamChainObj, String> p ) {
				ICFBamChainObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalDescription();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnDescription.setCellFactory( new Callback<TableColumn<ICFBamChainObj,String>,TableCell<ICFBamChainObj,String>>() {
			@Override public TableCell<ICFBamChainObj,String> call(
				TableColumn<ICFBamChainObj,String> arg)
			{
				return new CFStringTableCell<ICFBamChainObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDescription );
		tableColumnShortDescription = new TableColumn<ICFBamChainObj,String>( "Short Description" );
		tableColumnShortDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamChainObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamChainObj, String> p ) {
				ICFBamChainObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalShortDescription();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnShortDescription.setCellFactory( new Callback<TableColumn<ICFBamChainObj,String>,TableCell<ICFBamChainObj,String>>() {
			@Override public TableCell<ICFBamChainObj,String> call(
				TableColumn<ICFBamChainObj,String> arg)
			{
				return new CFStringTableCell<ICFBamChainObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortDescription );
		tableColumnLabel = new TableColumn<ICFBamChainObj,String>( "Label" );
		tableColumnLabel.setCellValueFactory( new Callback<CellDataFeatures<ICFBamChainObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamChainObj, String> p ) {
				ICFBamChainObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalLabel();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnLabel.setCellFactory( new Callback<TableColumn<ICFBamChainObj,String>,TableCell<ICFBamChainObj,String>>() {
			@Override public TableCell<ICFBamChainObj,String> call(
				TableColumn<ICFBamChainObj,String> arg)
			{
				return new CFStringTableCell<ICFBamChainObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLabel );
		tableColumnSuffix = new TableColumn<ICFBamChainObj,String>( "Suffix" );
		tableColumnSuffix.setCellValueFactory( new Callback<CellDataFeatures<ICFBamChainObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamChainObj, String> p ) {
				ICFBamChainObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalSuffix();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnSuffix.setCellFactory( new Callback<TableColumn<ICFBamChainObj,String>,TableCell<ICFBamChainObj,String>>() {
			@Override public TableCell<ICFBamChainObj,String> call(
				TableColumn<ICFBamChainObj,String> arg)
			{
				return new CFStringTableCell<ICFBamChainObj>();
			}
		});
		dataTable.getColumns().add( tableColumnSuffix );
		tableColumnLookupPrevRel = new TableColumn<ICFBamChainObj, ICFBamRelationObj>( "Previous Relation" );
		tableColumnLookupPrevRel.setCellValueFactory( new Callback<CellDataFeatures<ICFBamChainObj,ICFBamRelationObj>,ObservableValue<ICFBamRelationObj> >() {
			public ObservableValue<ICFBamRelationObj> call( CellDataFeatures<ICFBamChainObj, ICFBamRelationObj> p ) {
				ICFBamChainObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamRelationObj ref = obj.getRequiredLookupPrevRel();
					ReadOnlyObjectWrapper<ICFBamRelationObj> observable = new ReadOnlyObjectWrapper<ICFBamRelationObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupPrevRel.setCellFactory( new Callback<TableColumn<ICFBamChainObj,ICFBamRelationObj>,TableCell<ICFBamChainObj,ICFBamRelationObj>>() {
			@Override public TableCell<ICFBamChainObj,ICFBamRelationObj> call(
				TableColumn<ICFBamChainObj,ICFBamRelationObj> arg)
			{
				return new CFReferenceTableCell<ICFBamChainObj,ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupPrevRel );
		tableColumnLookupNextRel = new TableColumn<ICFBamChainObj, ICFBamRelationObj>( "Next Relation" );
		tableColumnLookupNextRel.setCellValueFactory( new Callback<CellDataFeatures<ICFBamChainObj,ICFBamRelationObj>,ObservableValue<ICFBamRelationObj> >() {
			public ObservableValue<ICFBamRelationObj> call( CellDataFeatures<ICFBamChainObj, ICFBamRelationObj> p ) {
				ICFBamChainObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamRelationObj ref = obj.getRequiredLookupNextRel();
					ReadOnlyObjectWrapper<ICFBamRelationObj> observable = new ReadOnlyObjectWrapper<ICFBamRelationObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupNextRel.setCellFactory( new Callback<TableColumn<ICFBamChainObj,ICFBamRelationObj>,TableCell<ICFBamChainObj,ICFBamRelationObj>>() {
			@Override public TableCell<ICFBamChainObj,ICFBamRelationObj> call(
				TableColumn<ICFBamChainObj,ICFBamRelationObj> arg)
			{
				return new CFReferenceTableCell<ICFBamChainObj,ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupNextRel );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFBamChainObj>() {
				@Override public void changed( ObservableValue<? extends ICFBamChainObj> observable,
					ICFBamChainObj oldValue,
					ICFBamChainObj newValue )
				{
					setJavaFXFocus( newValue );
				}
			});

		scrollMenu = new ScrollPane();
		scrollMenu.setVbarPolicy( ScrollBarPolicy.NEVER );
		scrollMenu.setHbarPolicy( ScrollBarPolicy.AS_NEEDED );
		scrollMenu.setFitToHeight( true );
		scrollMenu.setContent( getPanelHBoxMenu() );

		setTop( scrollMenu );
		setCenter( dataTable );
		javafxIsInitializing = false;
		if( observableListOfChain != null ) {
			dataTable.setItems( observableListOfChain );
		}
		adjustListButtons();
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

	public void setPaneMode( CFPane.PaneMode value ) {
		super.setPaneMode( value );
		adjustListButtons();
	}

	public void setJavaFXFocus( ICFLibAnyObj value ) {
		final String S_ProcName = "setJavaFXFocus";
		if( ( value == null ) || ( value instanceof ICFBamChainObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamChainObj" );
		}
		adjustListButtons();
	}

	public ICFBamChainObj getJavaFXFocusAsChain() {
		return( (ICFBamChainObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsChain( ICFBamChainObj value ) {
		setJavaFXFocus( value );
	}

	public class ChainByQualNameComparator
	implements Comparator<ICFBamChainObj>
	{
		public ChainByQualNameComparator() {
		}

		public int compare( ICFBamChainObj lhs, ICFBamChainObj rhs ) {
			if( lhs == null ) {
				if( rhs == null ) {
					return( 0 );
				}
				else {
					return( -1 );
				}
			}
			else if( rhs == null ) {
				return( 1 );
			}
			else {
				String lhsValue = lhs.getObjQualifiedName();
				String rhsValue = rhs.getObjQualifiedName();
				if( lhsValue == null ) {
					if( rhsValue == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhsValue == null ) {
					return( 1 );
				}
				else {
					return( lhsValue.compareTo( rhsValue ) );
				}
			}
		}
	}

	protected ChainByQualNameComparator compareChainByQualName = new ChainByQualNameComparator();

	public Collection<ICFBamChainObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFBamChainObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfChain = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
				Iterator<ICFBamChainObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfChain.add( iter.next() );
				}
				observableListOfChain.sort( compareChainByQualName );
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfChain );
			// Hack from stackoverflow to fix JavaFX TableView refresh issue
			((TableColumn)dataTable.getColumns().get(0)).setVisible( false );
			((TableColumn)dataTable.getColumns().get(0)).setVisible( true );
		}
	}

	protected class CompareCFButtonByText
	implements Comparator<CFButton>
	{
		public CompareCFButtonByText() {
		}

		@Override public int compare( CFButton lhs, CFButton rhs ) {
			if( lhs == null ) {
				if( rhs == null ) {
					return( 0 );
				}
				else {
					return( -1 );
				}
			}
			else if( rhs == null ) {
				return( 1 );
			}
			else {
				int retval = lhs.getText().compareTo( rhs.getText() );
				return( retval );
			}
		}
	}

	public CFHBox getPanelHBoxMenu() {
		if( hboxMenu == null ) {
			hboxMenu = new CFHBox( 10 );
			buttonAddChain = new CFButton();
			buttonAddChain.setMinWidth( 200 );
			buttonAddChain.setText( "Add Chain" );
			buttonAddChain.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						ICFBamChainObj obj = (ICFBamChainObj)schemaObj.getChainTableObj().newInstance();
						ICFBamChainEditObj edit = (ICFBamChainEditObj)( obj.beginEdit() );
						if( edit == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"edit" );
						}
						ICFSecTenantObj secTenant = schemaObj.getSecTenant();
						edit.setRequiredOwnerTenant( secTenant );
						ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
						if( container == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"JavaFXContainer" );
						}
						edit.setRequiredContainerTable( container );
						CFBorderPane frame = javafxSchema.getChainFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
						ICFBamJavaFXChainPaneCommon jpanelCommon = (ICFBamJavaFXChainPaneCommon)frame;
						jpanelCommon.setJavaFXFocus( obj );
						jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
						cfFormManager.pushForm( frame );
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer != null ) && javafxContainer.getOptionalLookupDefSchema() == null ) {
				hboxMenu.getChildren().add( buttonAddChain );
			}
			buttonViewSelected = new CFButton();
			buttonViewSelected.setMinWidth( 200 );
			buttonViewSelected.setText( "View Selected" );
			buttonViewSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						ICFBamChainObj selectedInstance = getJavaFXFocusAsChain();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "CHN".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getChainFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXChainPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamChainObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonViewSelected );

			buttonEditSelected = new CFButton();
			buttonEditSelected.setMinWidth( 200 );
			buttonEditSelected.setText( "Edit Selected" );
			buttonEditSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						ICFBamChainObj selectedInstance = getJavaFXFocusAsChain();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "CHN".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getChainFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXChainPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamChainObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( javafxContainer.getOptionalLookupDefSchema() == null ) {
				hboxMenu.getChildren().add( buttonEditSelected );
			}

			buttonDeleteSelected = new CFButton();
			buttonDeleteSelected.setMinWidth( 200 );
			buttonDeleteSelected.setText( "Delete Selected" );
			buttonDeleteSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						ICFBamChainObj selectedInstance = getJavaFXFocusAsChain();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "CHN".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getChainFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFBamJavaFXChainPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamChainObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( javafxContainer.getOptionalLookupDefSchema() == null ) {
				hboxMenu.getChildren().add( buttonDeleteSelected );
			}

		}
		return( hboxMenu );
	}

	public ICFBamTableObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFBamTableObj value ) {
		javafxContainer = value;
	}

	public void refreshMe() {
		if( javafxRefreshCallback != null ) {
			javafxRefreshCallback.refreshMe();
		}
	}

	public void adjustListButtons() {
		boolean enableState;
		boolean inEditState;
		boolean allowAdds;
		boolean inAddMode = false;
		ICFBamChainObj selectedObj = getJavaFXFocusAsChain();
		CFPane.PaneMode mode = getPaneMode();
		if( mode == CFPane.PaneMode.Edit ) {
			inEditState = true;
			allowAdds = false;
		}
		else {
			inEditState = false;
			if( getJavaFXContainer() != null ) {
				if( getLeft() != null ) {
					allowAdds = false;
					inAddMode = true;
				}
				else if( javafxContainer.getOptionalLookupDefSchema() != null ) {
					allowAdds = false;
				}
				else if( javafxContainer.getOptionalComponentsChains().size() > 0 ) {
					allowAdds = false;
				}
				else {
					boolean inheritsChain = false;
					ICFBamTableObj cur = javafxContainer;
					ICFBamRelationObj rel;
					Iterator<ICFBamRelationObj> iter;
					ICFBamRelationObj relSuperclass;
					while( ( ! inheritsChain ) && ( cur != null ) ) {
						relSuperclass = null;
						iter = cur.getChildrenRelations().iterator();
						while( iter.hasNext() && ( relSuperclass == null ) ) {
							rel = iter.next();
							if( rel.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
								relSuperclass = rel;
							}
						}
						if( relSuperclass != null ) {
							cur = relSuperclass.getRequiredLookupToTable();
							if( cur.getOptionalComponentsChains().size() > 0 ) {
								inheritsChain = true;
							}
						}
						else {
							cur = null;
						}
					}
					if( inheritsChain ) {
						allowAdds = false;
					}
					else {
						allowAdds = true;
					}
				}
			}
			else {
				allowAdds = false;
			}
		}
		if( selectedObj == null ) {
			enableState = false;
		}
		else {
			if( ( ! inAddMode ) && ( ! inEditState ) ) {
				enableState = true;
			}
			else {
				enableState = false;
			}
		}

		if( buttonViewSelected != null ) {
			if( inEditState ) {
				buttonViewSelected.setDisable( true );
			}
			else {
				buttonViewSelected.setDisable( ! enableState );
			}
		}
		if( buttonEditSelected != null ) {
			if( inEditState ) {
				buttonEditSelected.setDisable( true );
			}
			else if( javafxContainer.getOptionalLookupDefSchema() != null ) {
				buttonEditSelected.setDisable( true );
			}
			else {
				buttonEditSelected.setDisable( ! enableState );
			}
		}
		if( buttonDeleteSelected != null ) {
			if( javafxContainer.getOptionalLookupDefSchema() != null ) {
				buttonDeleteSelected.setDisable( true );
			}
			else {
				buttonDeleteSelected.setDisable( ! enableState );
			}
		}
		if( buttonAddChain != null ) {
			buttonAddChain.setDisable( ! allowAdds );
		}

	}
}
