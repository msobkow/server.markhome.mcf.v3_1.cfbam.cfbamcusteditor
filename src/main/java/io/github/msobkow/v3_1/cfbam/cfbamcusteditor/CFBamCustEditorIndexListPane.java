// Description: Java 25 JavaFX List of Obj Pane implementation for Index.

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
 *	CFBamJavaFXIndexListPane JavaFX List of Obj Pane implementation
 *	for Index.
 */
public class CFBamCustEditorIndexListPane
extends CFBorderPane
implements ICFBamJavaFXIndexPaneList
{
	public static String S_FormName = "List Index";
	protected ICFBamJavaFXSchema javafxSchema = null;
	protected Collection<ICFBamIndexObj> javafxDataCollection = null;
	protected ObservableList<ICFBamIndexObj> observableListOfIndex = null;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected CFButton buttonAddIndex = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected TableView<ICFBamIndexObj> dataTable = null;
	protected TableColumn<ICFBamIndexObj, Long> tableColumnId = null;
	protected TableColumn<ICFBamIndexObj, String> tableColumnName = null;
	protected TableColumn<ICFBamIndexObj, String> tableColumnShortName = null;
	protected TableColumn<ICFBamIndexObj, String> tableColumnLabel = null;
	protected TableColumn<ICFBamIndexObj, String> tableColumnShortDescription = null;
	protected TableColumn<ICFBamIndexObj, String> tableColumnDescription = null;
	protected TableColumn<ICFBamIndexObj, String> tableColumnDbName = null;
	protected TableColumn<ICFBamIndexObj, String> tableColumnSuffix = null;
	protected TableColumn<ICFBamIndexObj, Boolean> tableColumnIsUnique = null;
	protected TableColumn<ICFBamIndexObj, Boolean> tableColumnIsDbMapped = null;
	protected TableColumn<ICFBamIndexObj, ICFBamSchemaDefObj> tableColumnLookupDefSchema = null;

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


	public CFBamCustEditorIndexListPane( ICFFormManager formManager,
		ICFBamJavaFXSchema argSchema,
		ICFBamTableObj argContainer,
		ICFBamIndexObj argFocus,
		Collection<ICFBamIndexObj> argDataCollection,
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
		dataTable = new TableView<ICFBamIndexObj>();
		tableColumnId = new TableColumn<ICFBamIndexObj,Long>( "Id" );
		tableColumnId.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexObj,Long>,ObservableValue<Long> >() {
			public ObservableValue<Long> call( CellDataFeatures<ICFBamIndexObj, Long> p ) {
				ICFBamScopeObj obj = p.getValue();
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
		tableColumnId.setCellFactory( new Callback<TableColumn<ICFBamIndexObj,Long>,TableCell<ICFBamIndexObj,Long>>() {
			@Override public TableCell<ICFBamIndexObj,Long> call(
				TableColumn<ICFBamIndexObj,Long> arg)
			{
				return new CFInt64TableCell<ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnId );
		tableColumnName = new TableColumn<ICFBamIndexObj,String>( "Name" );
		tableColumnName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamIndexObj, String> p ) {
				ICFBamIndexObj obj = p.getValue();
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
		tableColumnName.setCellFactory( new Callback<TableColumn<ICFBamIndexObj,String>,TableCell<ICFBamIndexObj,String>>() {
			@Override public TableCell<ICFBamIndexObj,String> call(
				TableColumn<ICFBamIndexObj,String> arg)
			{
				return new CFStringTableCell<ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnName );
		tableColumnLookupDefSchema = new TableColumn<ICFBamIndexObj, ICFBamSchemaDefObj>( "Defining Schema" );
		tableColumnLookupDefSchema.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexObj,ICFBamSchemaDefObj>,ObservableValue<ICFBamSchemaDefObj> >() {
			public ObservableValue<ICFBamSchemaDefObj> call( CellDataFeatures<ICFBamIndexObj, ICFBamSchemaDefObj> p ) {
				ICFBamIndexObj obj = p.getValue();
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
		tableColumnLookupDefSchema.setCellFactory( new Callback<TableColumn<ICFBamIndexObj,ICFBamSchemaDefObj>,TableCell<ICFBamIndexObj,ICFBamSchemaDefObj>>() {
			@Override public TableCell<ICFBamIndexObj,ICFBamSchemaDefObj> call(
				TableColumn<ICFBamIndexObj,ICFBamSchemaDefObj> arg)
			{
				return new CFReferenceTableCell<ICFBamIndexObj,ICFBamSchemaDefObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupDefSchema );
		tableColumnDbName = new TableColumn<ICFBamIndexObj,String>( "Db Name" );
		tableColumnDbName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamIndexObj, String> p ) {
				ICFBamIndexObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalDbName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnDbName.setCellFactory( new Callback<TableColumn<ICFBamIndexObj,String>,TableCell<ICFBamIndexObj,String>>() {
			@Override public TableCell<ICFBamIndexObj,String> call(
				TableColumn<ICFBamIndexObj,String> arg)
			{
				return new CFStringTableCell<ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDbName );
		tableColumnShortName = new TableColumn<ICFBamIndexObj,String>( "Short Name" );
		tableColumnShortName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamIndexObj, String> p ) {
				ICFBamIndexObj obj = p.getValue();
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
		tableColumnShortName.setCellFactory( new Callback<TableColumn<ICFBamIndexObj,String>,TableCell<ICFBamIndexObj,String>>() {
			@Override public TableCell<ICFBamIndexObj,String> call(
				TableColumn<ICFBamIndexObj,String> arg)
			{
				return new CFStringTableCell<ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortName );
		tableColumnDescription = new TableColumn<ICFBamIndexObj,String>( "Description" );
		tableColumnDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamIndexObj, String> p ) {
				ICFBamIndexObj obj = p.getValue();
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
		tableColumnDescription.setCellFactory( new Callback<TableColumn<ICFBamIndexObj,String>,TableCell<ICFBamIndexObj,String>>() {
			@Override public TableCell<ICFBamIndexObj,String> call(
				TableColumn<ICFBamIndexObj,String> arg)
			{
				return new CFStringTableCell<ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDescription );
		tableColumnShortDescription = new TableColumn<ICFBamIndexObj,String>( "Short Description" );
		tableColumnShortDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamIndexObj, String> p ) {
				ICFBamIndexObj obj = p.getValue();
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
		tableColumnShortDescription.setCellFactory( new Callback<TableColumn<ICFBamIndexObj,String>,TableCell<ICFBamIndexObj,String>>() {
			@Override public TableCell<ICFBamIndexObj,String> call(
				TableColumn<ICFBamIndexObj,String> arg)
			{
				return new CFStringTableCell<ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortDescription );
		tableColumnLabel = new TableColumn<ICFBamIndexObj,String>( "Label" );
		tableColumnLabel.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamIndexObj, String> p ) {
				ICFBamIndexObj obj = p.getValue();
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
		tableColumnLabel.setCellFactory( new Callback<TableColumn<ICFBamIndexObj,String>,TableCell<ICFBamIndexObj,String>>() {
			@Override public TableCell<ICFBamIndexObj,String> call(
				TableColumn<ICFBamIndexObj,String> arg)
			{
				return new CFStringTableCell<ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLabel );
		tableColumnIsDbMapped = new TableColumn<ICFBamIndexObj,Boolean>( "Is Db Mapped" );
		tableColumnIsDbMapped.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamIndexObj, Boolean> p ) {
				ICFBamIndexObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredIsDbMapped();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnIsDbMapped.setCellFactory( new Callback<TableColumn<ICFBamIndexObj,Boolean>,TableCell<ICFBamIndexObj,Boolean>>() {
			@Override public TableCell<ICFBamIndexObj,Boolean> call(
				TableColumn<ICFBamIndexObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIsDbMapped );
		tableColumnIsUnique = new TableColumn<ICFBamIndexObj,Boolean>( "Is Unique" );
		tableColumnIsUnique.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamIndexObj, Boolean> p ) {
				ICFBamIndexObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredIsUnique();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnIsUnique.setCellFactory( new Callback<TableColumn<ICFBamIndexObj,Boolean>,TableCell<ICFBamIndexObj,Boolean>>() {
			@Override public TableCell<ICFBamIndexObj,Boolean> call(
				TableColumn<ICFBamIndexObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIsUnique );
		tableColumnSuffix = new TableColumn<ICFBamIndexObj,String>( "Suffix" );
		tableColumnSuffix.setCellValueFactory( new Callback<CellDataFeatures<ICFBamIndexObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamIndexObj, String> p ) {
				ICFBamIndexObj obj = p.getValue();
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
		tableColumnSuffix.setCellFactory( new Callback<TableColumn<ICFBamIndexObj,String>,TableCell<ICFBamIndexObj,String>>() {
			@Override public TableCell<ICFBamIndexObj,String> call(
				TableColumn<ICFBamIndexObj,String> arg)
			{
				return new CFStringTableCell<ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnSuffix );

		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFBamIndexObj>() {
				@Override public void changed( ObservableValue<? extends ICFBamIndexObj> observable,
					ICFBamIndexObj oldValue,
					ICFBamIndexObj newValue )
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
		if( observableListOfIndex != null ) {
			dataTable.setItems( observableListOfIndex );
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
		if( ( value == null ) || ( value instanceof ICFBamIndexObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamIndexObj" );
		}
		adjustListButtons();
	}

	public ICFBamIndexObj getJavaFXFocusAsIndex() {
		return( (ICFBamIndexObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsIndex( ICFBamIndexObj value ) {
		setJavaFXFocus( value );
	}

	public class IndexByQualNameComparator
	implements Comparator<ICFBamIndexObj>
	{
		public IndexByQualNameComparator() {
		}

		public int compare( ICFBamIndexObj lhs, ICFBamIndexObj rhs ) {
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

	protected IndexByQualNameComparator compareIndexByQualName = new IndexByQualNameComparator();

	public Collection<ICFBamIndexObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFBamIndexObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfIndex = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
				Iterator<ICFBamIndexObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfIndex.add( iter.next() );
				}
				observableListOfIndex.sort( compareIndexByQualName );
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfIndex );
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
			buttonAddIndex = new CFButton();
			buttonAddIndex.setMinWidth( 200 );
			buttonAddIndex.setText( "Add Index" );
			buttonAddIndex.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						ICFBamIndexObj obj = (ICFBamIndexObj)schemaObj.getIndexTableObj().newInstance();
						ICFBamIndexEditObj edit = (ICFBamIndexEditObj)( obj.beginEdit() );
						if( edit == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"edit" );
						}
						ICFSecTenantObj secTenant = schemaObj.getSecTenant();
						edit.setRequiredOwnerIdxTenant( secTenant );
						ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
						if( container == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"JavaFXContainer" );
						}
						edit.setRequiredContainerTable( container );
						CFBorderPane frame = javafxSchema.getIndexFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
						ICFBamJavaFXIndexPaneCommon jpanelCommon = (ICFBamJavaFXIndexPaneCommon)frame;
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
				hboxMenu.getChildren().add( buttonAddIndex );
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
						ICFBamIndexObj selectedInstance = getJavaFXFocusAsIndex();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "IDXD".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getIndexFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXIndexPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamIndexObj" );
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
						ICFBamIndexObj selectedInstance = getJavaFXFocusAsIndex();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "IDXD".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getIndexFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXIndexPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamIndexObj" );
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
						ICFBamIndexObj selectedInstance = getJavaFXFocusAsIndex();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "IDXD".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getIndexFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFBamJavaFXIndexPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamIndexObj" );
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
		ICFBamIndexObj selectedObj = getJavaFXFocusAsIndex();
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
				else if( ( getJavaFXContainer() == null ) || ( getJavaFXContainer().getOptionalLookupDefSchema() != null ) ) {
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

		if( buttonAddIndex != null ) {
			buttonAddIndex.setDisable( ! allowAdds );
		}
	}
}
