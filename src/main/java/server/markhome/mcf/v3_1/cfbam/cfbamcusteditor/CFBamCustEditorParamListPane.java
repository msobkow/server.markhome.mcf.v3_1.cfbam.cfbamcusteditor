// Description: Java 25 JavaFX List of Obj Pane implementation for Param.

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
 *	CFBamJavaFXParamListPane JavaFX List of Obj Pane implementation
 *	for Param.
 */
public class CFBamCustEditorParamListPane
extends CFBorderPane
implements ICFBamJavaFXParamPaneList
{
	public static String S_FormName = "List Param";
	protected ICFBamJavaFXSchema javafxSchema = null;
	protected Collection<ICFBamParamObj> javafxDataCollection = null;
	protected ObservableList<ICFBamParamObj> observableListOfParam = null;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected CFButton buttonAddParam = null;
	protected CFButton buttonMoveUpSelected = null;
	protected CFButton buttonMoveDownSelected = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected TableView<ICFBamParamObj> dataTable = null;
	protected TableColumn<ICFBamParamObj, Long> tableColumnId = null;
	protected TableColumn<ICFBamParamObj, String> tableColumnName = null;
	protected TableColumn<ICFBamParamObj, String> tableColumnShortDescription = null;
	protected TableColumn<ICFBamParamObj, String> tableColumnDescription = null;
	protected TableColumn<ICFBamParamObj, Boolean> tableColumnIsNullable = null;
	protected TableColumn<ICFBamParamObj, ICFBamSchemaDefObj> tableColumnLookupDefSchema = null;
	protected TableColumn<ICFBamParamObj, ICFBamValueObj> tableColumnLookupType = null;

	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected boolean javafxIsInitializing = true;
	protected boolean javafxSortByChain = false;
	protected ICFBamServerMethodObj javafxContainer = null;
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


	public CFBamCustEditorParamListPane( ICFFormManager formManager,
		ICFBamJavaFXSchema argSchema,
		ICFBamServerMethodObj argContainer,
		ICFBamParamObj argFocus,
		Collection<ICFBamParamObj> argDataCollection,
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
		dataTable = new TableView<ICFBamParamObj>();
		tableColumnId = new TableColumn<ICFBamParamObj,Long>( "Id" );
		tableColumnId.setCellValueFactory( new Callback<CellDataFeatures<ICFBamParamObj,Long>,ObservableValue<Long> >() {
			public ObservableValue<Long> call( CellDataFeatures<ICFBamParamObj, Long> p ) {
				ICFBamParamObj obj = p.getValue();
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
		tableColumnId.setCellFactory( new Callback<TableColumn<ICFBamParamObj,Long>,TableCell<ICFBamParamObj,Long>>() {
			@Override public TableCell<ICFBamParamObj,Long> call(
				TableColumn<ICFBamParamObj,Long> arg)
			{
				return new CFInt64TableCell<ICFBamParamObj>();
			}
		});
		dataTable.getColumns().add( tableColumnId );
		tableColumnName = new TableColumn<ICFBamParamObj,String>( "Name" );
		tableColumnName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamParamObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamParamObj, String> p ) {
				ICFBamParamObj obj = p.getValue();
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
		tableColumnName.setCellFactory( new Callback<TableColumn<ICFBamParamObj,String>,TableCell<ICFBamParamObj,String>>() {
			@Override public TableCell<ICFBamParamObj,String> call(
				TableColumn<ICFBamParamObj,String> arg)
			{
				return new CFStringTableCell<ICFBamParamObj>();
			}
		});
		dataTable.getColumns().add( tableColumnName );
		tableColumnLookupDefSchema = new TableColumn<ICFBamParamObj, ICFBamSchemaDefObj>( "Defining Schema" );
		tableColumnLookupDefSchema.setCellValueFactory( new Callback<CellDataFeatures<ICFBamParamObj,ICFBamSchemaDefObj>,ObservableValue<ICFBamSchemaDefObj> >() {
			public ObservableValue<ICFBamSchemaDefObj> call( CellDataFeatures<ICFBamParamObj, ICFBamSchemaDefObj> p ) {
				ICFBamParamObj obj = p.getValue();
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
		tableColumnLookupDefSchema.setCellFactory( new Callback<TableColumn<ICFBamParamObj,ICFBamSchemaDefObj>,TableCell<ICFBamParamObj,ICFBamSchemaDefObj>>() {
			@Override public TableCell<ICFBamParamObj,ICFBamSchemaDefObj> call(
				TableColumn<ICFBamParamObj,ICFBamSchemaDefObj> arg)
			{
				return new CFReferenceTableCell<ICFBamParamObj,ICFBamSchemaDefObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupDefSchema );
		tableColumnDescription = new TableColumn<ICFBamParamObj,String>( "Description" );
		tableColumnDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamParamObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamParamObj, String> p ) {
				ICFBamParamObj obj = p.getValue();
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
		tableColumnDescription.setCellFactory( new Callback<TableColumn<ICFBamParamObj,String>,TableCell<ICFBamParamObj,String>>() {
			@Override public TableCell<ICFBamParamObj,String> call(
				TableColumn<ICFBamParamObj,String> arg)
			{
				return new CFStringTableCell<ICFBamParamObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDescription );
		tableColumnShortDescription = new TableColumn<ICFBamParamObj,String>( "Short Description" );
		tableColumnShortDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamParamObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamParamObj, String> p ) {
				ICFBamParamObj obj = p.getValue();
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
		tableColumnShortDescription.setCellFactory( new Callback<TableColumn<ICFBamParamObj,String>,TableCell<ICFBamParamObj,String>>() {
			@Override public TableCell<ICFBamParamObj,String> call(
				TableColumn<ICFBamParamObj,String> arg)
			{
				return new CFStringTableCell<ICFBamParamObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortDescription );
		tableColumnLookupType = new TableColumn<ICFBamParamObj, ICFBamValueObj>( "Type Specification" );
		tableColumnLookupType.setCellValueFactory( new Callback<CellDataFeatures<ICFBamParamObj,ICFBamValueObj>,ObservableValue<ICFBamValueObj> >() {
			public ObservableValue<ICFBamValueObj> call( CellDataFeatures<ICFBamParamObj, ICFBamValueObj> p ) {
				ICFBamParamObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamValueObj ref = obj.getRequiredLookupType();
					ReadOnlyObjectWrapper<ICFBamValueObj> observable = new ReadOnlyObjectWrapper<ICFBamValueObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupType.setCellFactory( new Callback<TableColumn<ICFBamParamObj,ICFBamValueObj>,TableCell<ICFBamParamObj,ICFBamValueObj>>() {
			@Override public TableCell<ICFBamParamObj,ICFBamValueObj> call(
				TableColumn<ICFBamParamObj,ICFBamValueObj> arg)
			{
				return new CFReferenceTableCell<ICFBamParamObj,ICFBamValueObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupType );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFBamParamObj>() {
				@Override public void changed( ObservableValue<? extends ICFBamParamObj> observable,
					ICFBamParamObj oldValue,
					ICFBamParamObj newValue )
				{
					setJavaFXFocus( newValue );
				}
			});
		tableColumnIsNullable = new TableColumn<ICFBamParamObj,Boolean>( "Is Nullable" );
		tableColumnIsNullable.setCellValueFactory( new Callback<CellDataFeatures<ICFBamParamObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamParamObj, Boolean> p ) {
				ICFBamParamObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredIsNullable();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnIsNullable.setCellFactory( new Callback<TableColumn<ICFBamParamObj,Boolean>,TableCell<ICFBamParamObj,Boolean>>() {
			@Override public TableCell<ICFBamParamObj,Boolean> call(
				TableColumn<ICFBamParamObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamParamObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIsNullable );

		scrollMenu = new ScrollPane();
		scrollMenu.setVbarPolicy( ScrollBarPolicy.NEVER );
		scrollMenu.setHbarPolicy( ScrollBarPolicy.AS_NEEDED );
		scrollMenu.setFitToHeight( true );
		scrollMenu.setContent( getPanelHBoxMenu() );

		setTop( scrollMenu );
		setCenter( dataTable );
		javafxIsInitializing = false;
		if( observableListOfParam != null ) {
			dataTable.setItems( observableListOfParam );
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
		if( ( value == null ) || ( value instanceof ICFBamParamObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamParamObj" );
		}
		adjustListButtons();
	}

	public ICFBamParamObj getJavaFXFocusAsParam() {
		return( (ICFBamParamObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsParam( ICFBamParamObj value ) {
		setJavaFXFocus( value );
	}

	public class ParamByQualNameComparator
	implements Comparator<ICFBamParamObj>
	{
		public ParamByQualNameComparator() {
		}

		public int compare( ICFBamParamObj lhs, ICFBamParamObj rhs ) {
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

	protected ParamByQualNameComparator compareParamByQualName = new ParamByQualNameComparator();

	public Collection<ICFBamParamObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFBamParamObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfParam = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
			if( javafxSortByChain ) {
				Iterator<ICFBamParamObj> iter = javafxDataCollection.iterator();
				ICFBamParamObj head = null;
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
				ICFBamParamObj cur = head;
				while( cur != null ) {
					observableListOfParam.add( cur );
					cur = cur.getOptionalLookupNext();
				}
			}
			else {
				Iterator<ICFBamParamObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfParam.add( iter.next() );
				}
				observableListOfParam.sort( compareParamByQualName );
			}
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfParam );
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
			buttonAddParam = new CFButton();
			buttonAddParam.setMinWidth( 200 );
			buttonAddParam.setText( "Add Param" );
			buttonAddParam.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						ICFBamParamObj obj = (ICFBamParamObj)schemaObj.getParamTableObj().newInstance();
						ICFBamParamEditObj edit = (ICFBamParamEditObj)( obj.beginEdit() );
						if( edit == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"edit" );
						}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamServerMethodObj container = (ICFBamServerMethodObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerServerMeth( container );
						CFBorderPane frame = javafxSchema.getParamFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
						ICFBamJavaFXParamPaneCommon jpanelCommon = (ICFBamJavaFXParamPaneCommon)frame;
						jpanelCommon.setJavaFXFocus( obj );
						jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
						cfFormManager.pushForm( frame );
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonAddParam );
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
						ICFBamParamObj selectedInstance = getJavaFXFocusAsParam();
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
			hboxMenu.getChildren().add( buttonMoveUpSelected );

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
						ICFBamParamObj selectedInstance = getJavaFXFocusAsParam();
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
			hboxMenu.getChildren().add( buttonMoveDownSelected );

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
						ICFBamParamObj selectedInstance = getJavaFXFocusAsParam();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "SPRM".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getParamFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXParamPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamParamObj" );
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
						ICFBamParamObj selectedInstance = getJavaFXFocusAsParam();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "SPRM".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getParamFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXParamPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamParamObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonEditSelected );

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
						ICFBamParamObj selectedInstance = getJavaFXFocusAsParam();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "SPRM".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getParamFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFBamJavaFXParamPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamParamObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonDeleteSelected );

		}
		return( hboxMenu );
	}

	public ICFBamServerMethodObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFBamServerMethodObj value ) {
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
		ICFBamParamObj selectedObj = getJavaFXFocusAsParam();
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

		if( buttonAddParam != null ) {
			buttonAddParam.setDisable( ! allowAdds );
		}
	}
}
