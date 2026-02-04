// Description: Java 25 JavaFX List of Obj Pane implementation for IndexCol.

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
 *	CFBamJavaFXIndexColListPane JavaFX List of Obj Pane implementation
 *	for IndexCol.
 */
public class CFBamCustEditorIndexColListPane
extends CFBorderPane
implements ICFBamJavaFXIndexColPaneList
{
	public static String S_FormName = "List IndexCol";
	protected ICFBamJavaFXSchema javafxSchema = null;
	protected Collection<ICFBamIndexColObj> javafxDataCollection = null;
	protected ObservableList<ICFBamIndexColObj> observableListOfIndexCol = null;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected CFButton buttonAddIndexCol = null;
	protected CFButton buttonMoveUpSelected = null;
	protected CFButton buttonMoveDownSelected = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected TableView<ICFBamIndexColObj> dataTable = null;
	protected TableColumn<ICFBamIndexColObj, Long> tableColumnId = null;
	protected TableColumn<ICFBamIndexColObj, String> tableColumnName = null;
	protected TableColumn<ICFBamIndexColObj, String> tableColumnShortName = null;
	protected TableColumn<ICFBamIndexColObj, String> tableColumnLabel = null;
	protected TableColumn<ICFBamIndexColObj, String> tableColumnShortDescription = null;
	protected TableColumn<ICFBamIndexColObj, String> tableColumnDescription = null;
	protected TableColumn<ICFBamIndexColObj, Boolean> tableColumnIsAscending = null;
	protected TableColumn<ICFBamIndexColObj, ICFBamSchemaDefObj> tableColumnLookupDefSchema = null;
	protected TableColumn<ICFBamIndexColObj, ICFBamValueObj> tableColumnLookupColumn = null;

	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected boolean javafxIsInitializing = true;
	protected boolean javafxSortByChain = false;
	protected ICFBamIndexObj javafxContainer = null;
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


	public CFBamCustEditorIndexColListPane( ICFFormManager formManager,
		ICFBamJavaFXSchema argSchema,
		ICFBamIndexObj argContainer,
		ICFBamIndexColObj argFocus,
		Collection<ICFBamIndexColObj> argDataCollection,
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
		dataTable = new TableView<ICFBamIndexColObj>();
		tableColumnId = new TableColumn<ICFBamIndexColObj,Long>( "Id" );
		tableColumnId.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexColObj,Long>,ObservableValue<Long> >() {
			public ObservableValue<Long> call( CellDataFeatures<ICFBamIndexColObj, Long> p ) {
				ICFBamIndexColObj obj = p.getValue();
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
		tableColumnId.setCellFactory( new Callback<TableColumn<ICFBamIndexColObj,Long>,TableCell<ICFBamIndexColObj,Long>>() {
			@Override public TableCell<ICFBamIndexColObj,Long> call(
				TableColumn<ICFBamIndexColObj,Long> arg)
			{
				return new CFInt64TableCell<ICFBamIndexColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnId );
		tableColumnName = new TableColumn<ICFBamIndexColObj,String>( "Name" );
		tableColumnName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexColObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamIndexColObj, String> p ) {
				ICFBamIndexColObj obj = p.getValue();
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
		tableColumnName.setCellFactory( new Callback<TableColumn<ICFBamIndexColObj,String>,TableCell<ICFBamIndexColObj,String>>() {
			@Override public TableCell<ICFBamIndexColObj,String> call(
				TableColumn<ICFBamIndexColObj,String> arg)
			{
				return new CFStringTableCell<ICFBamIndexColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnName );
		tableColumnLookupDefSchema = new TableColumn<ICFBamIndexColObj, ICFBamSchemaDefObj>( "Defining Schema" );
		tableColumnLookupDefSchema.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexColObj,ICFBamSchemaDefObj>,ObservableValue<ICFBamSchemaDefObj> >() {
			public ObservableValue<ICFBamSchemaDefObj> call( CellDataFeatures<ICFBamIndexColObj, ICFBamSchemaDefObj> p ) {
				ICFBamIndexColObj obj = p.getValue();
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
		tableColumnLookupDefSchema.setCellFactory( new Callback<TableColumn<ICFBamIndexColObj,ICFBamSchemaDefObj>,TableCell<ICFBamIndexColObj,ICFBamSchemaDefObj>>() {
			@Override public TableCell<ICFBamIndexColObj,ICFBamSchemaDefObj> call(
				TableColumn<ICFBamIndexColObj,ICFBamSchemaDefObj> arg)
			{
				return new CFReferenceTableCell<ICFBamIndexColObj,ICFBamSchemaDefObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupDefSchema );
		tableColumnShortName = new TableColumn<ICFBamIndexColObj,String>( "Short Name" );
		tableColumnShortName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexColObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamIndexColObj, String> p ) {
				ICFBamIndexColObj obj = p.getValue();
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
		tableColumnShortName.setCellFactory( new Callback<TableColumn<ICFBamIndexColObj,String>,TableCell<ICFBamIndexColObj,String>>() {
			@Override public TableCell<ICFBamIndexColObj,String> call(
				TableColumn<ICFBamIndexColObj,String> arg)
			{
				return new CFStringTableCell<ICFBamIndexColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortName );
		tableColumnDescription = new TableColumn<ICFBamIndexColObj,String>( "Description" );
		tableColumnDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexColObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamIndexColObj, String> p ) {
				ICFBamIndexColObj obj = p.getValue();
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
		tableColumnDescription.setCellFactory( new Callback<TableColumn<ICFBamIndexColObj,String>,TableCell<ICFBamIndexColObj,String>>() {
			@Override public TableCell<ICFBamIndexColObj,String> call(
				TableColumn<ICFBamIndexColObj,String> arg)
			{
				return new CFStringTableCell<ICFBamIndexColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDescription );
		tableColumnShortDescription = new TableColumn<ICFBamIndexColObj,String>( "Short Description" );
		tableColumnShortDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexColObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamIndexColObj, String> p ) {
				ICFBamIndexColObj obj = p.getValue();
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
		tableColumnShortDescription.setCellFactory( new Callback<TableColumn<ICFBamIndexColObj,String>,TableCell<ICFBamIndexColObj,String>>() {
			@Override public TableCell<ICFBamIndexColObj,String> call(
				TableColumn<ICFBamIndexColObj,String> arg)
			{
				return new CFStringTableCell<ICFBamIndexColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortDescription );
		tableColumnLabel = new TableColumn<ICFBamIndexColObj,String>( "Label" );
		tableColumnLabel.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexColObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamIndexColObj, String> p ) {
				ICFBamIndexColObj obj = p.getValue();
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
		tableColumnLabel.setCellFactory( new Callback<TableColumn<ICFBamIndexColObj,String>,TableCell<ICFBamIndexColObj,String>>() {
			@Override public TableCell<ICFBamIndexColObj,String> call(
				TableColumn<ICFBamIndexColObj,String> arg)
			{
				return new CFStringTableCell<ICFBamIndexColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLabel );
		tableColumnLookupColumn = new TableColumn<ICFBamIndexColObj, ICFBamValueObj>( "Column" );
		tableColumnLookupColumn.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexColObj,ICFBamValueObj>,ObservableValue<ICFBamValueObj> >() {
			public ObservableValue<ICFBamValueObj> call( CellDataFeatures<ICFBamIndexColObj, ICFBamValueObj> p ) {
				ICFBamIndexColObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamValueObj ref = obj.getRequiredLookupColumn();
					ReadOnlyObjectWrapper<ICFBamValueObj> observable = new ReadOnlyObjectWrapper<ICFBamValueObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupColumn.setCellFactory( new Callback<TableColumn<ICFBamIndexColObj,ICFBamValueObj>,TableCell<ICFBamIndexColObj,ICFBamValueObj>>() {
			@Override public TableCell<ICFBamIndexColObj,ICFBamValueObj> call(
				TableColumn<ICFBamIndexColObj,ICFBamValueObj> arg)
			{
				return new CFReferenceTableCell<ICFBamIndexColObj,ICFBamValueObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupColumn );
		tableColumnIsAscending = new TableColumn<ICFBamIndexColObj,Boolean>( "Is Ascending" );
		tableColumnIsAscending.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexColObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamIndexColObj, Boolean> p ) {
				ICFBamIndexColObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredIsAscending();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnIsAscending.setCellFactory( new Callback<TableColumn<ICFBamIndexColObj,Boolean>,TableCell<ICFBamIndexColObj,Boolean>>() {
			@Override public TableCell<ICFBamIndexColObj,Boolean> call(
				TableColumn<ICFBamIndexColObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamIndexColObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIsAscending );

		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFBamIndexColObj>() {
				@Override public void changed( ObservableValue<? extends ICFBamIndexColObj> observable,
					ICFBamIndexColObj oldValue,
					ICFBamIndexColObj newValue )
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
		if( observableListOfIndexCol != null ) {
			dataTable.setItems( observableListOfIndexCol );
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
		if( ( value == null ) || ( value instanceof ICFBamIndexColObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamIndexColObj" );
		}
		adjustListButtons();
	}

	public ICFBamIndexColObj getJavaFXFocusAsIndexCol() {
		return( (ICFBamIndexColObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsIndexCol( ICFBamIndexColObj value ) {
		setJavaFXFocus( value );
	}

	public class IndexColByQualNameComparator
	implements Comparator<ICFBamIndexColObj>
	{
		public IndexColByQualNameComparator() {
		}

		public int compare( ICFBamIndexColObj lhs, ICFBamIndexColObj rhs ) {
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

	protected IndexColByQualNameComparator compareIndexColByQualName = new IndexColByQualNameComparator();

	public Collection<ICFBamIndexColObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFBamIndexColObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfIndexCol = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
			if( javafxSortByChain ) {
				Iterator<ICFBamIndexColObj> iter = javafxDataCollection.iterator();
				ICFBamIndexColObj head = null;
				while( ( head == null ) && iter.hasNext() ) {
					head = iter.next();
					if( null != head.getOptionalLookupPrev() ) {
						head = null;
					}
				}
				if( ( head == null ) && ( javafxDataCollection.size() > 0 ) ) {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Could not locate head of object chain" );
				}
				ICFBamIndexColObj cur = head;
				while( cur != null ) {
					observableListOfIndexCol.add( cur );
					cur = cur.getOptionalLookupNext();
				}
			}
			else {
				Iterator<ICFBamIndexColObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfIndexCol.add( iter.next() );
				}
				observableListOfIndexCol.sort( compareIndexColByQualName );
			}
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfIndexCol );
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
			buttonAddIndexCol = new CFButton();
			buttonAddIndexCol.setMinWidth( 200 );
			buttonAddIndexCol.setText( "Add IndexCol" );
			buttonAddIndexCol.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						ICFBamIndexColObj obj = (ICFBamIndexColObj)schemaObj.getIndexColTableObj().newInstance();
						ICFBamIndexColEditObj edit = (ICFBamIndexColEditObj)( obj.beginEdit() );
						if( edit == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"edit" );
						}
		ICFSecTenantObj secTenant = schemaObj.getSecTenant();
		edit.setRequiredOwnerTenant( secTenant );
			ICFBamIndexObj container = (ICFBamIndexObj)( getJavaFXContainer() );
			if( container == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"JavaFXContainer" );
			}
			edit.setRequiredContainerIndex( container );
						CFBorderPane frame = javafxSchema.getIndexColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
						ICFBamJavaFXIndexColPaneCommon jpanelCommon = (ICFBamJavaFXIndexColPaneCommon)frame;
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
				hboxMenu.getChildren().add( buttonAddIndexCol );
			}
			buttonMoveUpSelected = new CFButton();
			buttonMoveUpSelected.setMinWidth( 200 );
			buttonMoveUpSelected.setText( "Move Up Selected" );
			buttonMoveUpSelected.setOnAction( new EventHandler<ActionEvent>() {
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
						ICFBamIndexColObj selectedInstance = getJavaFXFocusAsIndexCol();
						if( selectedInstance != null ) {
							if( null != selectedInstance.getOptionalLookupPrev() ) {
								selectedInstance.moveUp();
								refreshMe();
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer != null ) && javafxContainer.getOptionalLookupDefSchema() == null ) {
				hboxMenu.getChildren().add( buttonMoveUpSelected );
			}

			buttonMoveDownSelected = new CFButton();
			buttonMoveDownSelected.setMinWidth( 200 );
			buttonMoveDownSelected.setText( "Move Down Selected" );
			buttonMoveDownSelected.setOnAction( new EventHandler<ActionEvent>() {
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
						ICFBamIndexColObj selectedInstance = getJavaFXFocusAsIndexCol();
						if( selectedInstance != null ) {
							if( null != selectedInstance.getOptionalLookupNext() ) {
								selectedInstance.moveDown();
								refreshMe();
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer != null ) && javafxContainer.getOptionalLookupDefSchema() == null ) {
				hboxMenu.getChildren().add( buttonMoveDownSelected );
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
						ICFBamIndexColObj selectedInstance = getJavaFXFocusAsIndexCol();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "IDXC".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getIndexColFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXIndexColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamIndexColObj" );
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
						ICFBamIndexColObj selectedInstance = getJavaFXFocusAsIndexCol();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "IDXC".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getIndexColFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXIndexColPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamIndexColObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer != null ) && javafxContainer.getOptionalLookupDefSchema() == null ) {
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
						ICFBamIndexColObj selectedInstance = getJavaFXFocusAsIndexCol();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "IDXC".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getIndexColFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFBamJavaFXIndexColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamIndexColObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer != null ) && javafxContainer.getOptionalLookupDefSchema() == null ) {
				hboxMenu.getChildren().add( buttonDeleteSelected );
			}

		}
		return( hboxMenu );
	}

	public ICFBamIndexObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFBamIndexObj value ) {
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
		ICFBamIndexColObj selectedObj = getJavaFXFocusAsIndexCol();
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
				else {
					allowAdds = true;
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

		boolean enableMoveUp = ( ! inEditState )
			&& ( ! inAddMode )
			&& ( selectedObj != null )
			&& ( null != selectedObj.getOptionalLookupPrev() );
		boolean enableMoveDown = ( ! inEditState )
			&& ( ! inAddMode )
			&& ( selectedObj != null )
			&& ( null != selectedObj.getOptionalLookupNext() );
		if( buttonMoveUpSelected != null ) {
			if( inEditState ) {
				buttonMoveUpSelected.setDisable( true );
			}
			else if( ( selectedObj != null ) && ( selectedObj.getOptionalLookupDefSchema() == null ) ) {
				buttonMoveUpSelected.setDisable( ! enableMoveUp );
			}
			else {
				buttonMoveUpSelected.setDisable( true );
			}
		}
		if( buttonMoveDownSelected != null ) {
			if( inEditState ) {
				buttonMoveDownSelected.setDisable( true );
			}
			else if( ( selectedObj != null ) && ( selectedObj.getOptionalLookupDefSchema() == null ) ) {
				buttonMoveDownSelected.setDisable( ! enableMoveDown );
			}
			else {
				buttonMoveDownSelected.setDisable( true );
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
			else if( ( selectedObj != null ) && ( selectedObj.getOptionalLookupDefSchema() == null ) ) {
				buttonEditSelected.setDisable( ! enableState );
			}
			else {
				buttonEditSelected.setDisable( true );
			}
		}
		if( buttonDeleteSelected != null ) {
			if( inEditState ) {
				buttonDeleteSelected.setDisable( true );
			}
			else if( ( selectedObj != null ) && ( selectedObj.getOptionalLookupDefSchema() == null ) ) {
				buttonDeleteSelected.setDisable( ! enableState );
			}
			else {
				buttonDeleteSelected.setDisable( true );
			}
		}

		if( buttonAddIndexCol != null ) {
			buttonAddIndexCol.setDisable( ! allowAdds );
		}

	}
}
