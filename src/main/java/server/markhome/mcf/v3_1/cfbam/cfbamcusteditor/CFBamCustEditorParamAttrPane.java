// Description: Java 25 JavaFX Attribute Pane implementation for Param.

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
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;

/**
 *	CFBamJavaFXParamAttrPane JavaFX Attribute Pane implementation
 *	for Param.
 */
public class CFBamCustEditorParamAttrPane
extends CFGridPane
implements ICFBamJavaFXParamPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFBamJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected class DefSchemaCFLabel
		extends CFLabel
	{
		public DefSchemaCFLabel() {
			super();
			setText( "Defining Schema" );
		}
	}

	protected class CallbackDefSchemaChosen
	implements ICFBamJavaFXSchemaDefChosen
	{
		public CallbackDefSchemaChosen() {
		}

		public void choseSchemaDef( ICFBamSchemaDefObj value ) {
			if( javafxReferenceLookupDefSchema != null ) {
				ICFBamParamObj cur = getJavaFXFocusAsParam();
				if( cur != null ) {
					ICFBamParamEditObj editObj = (ICFBamParamEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupDefSchema.setReferencedObject( value );
							editObj.setOptionalLookupDefSchema( value );
						}
					}
				}
			}
		}
	}

	protected class DefSchemaReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			ICFBamParamObj focus = getJavaFXFocusAsParam();
			ICFBamParamEditObj editObj  = (ICFBamParamEditObj)focus.getEdit();
			if( editObj != null ) {
				focus = editObj;
			}
			ICFBamSchemaDefObj referencedObj = (ICFBamSchemaDefObj)javafxReferenceLookupDefSchema.getReferencedObject();
			Collection<ICFBamSchemaDefObj> cltn = null;
			CFBorderPane form = javafxSchema.getSchemaDefFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackDefSchemaChosen() );
			((ICFBamJavaFXSchemaDefPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamParamObj focus = getJavaFXFocusAsParam();
			if( focus != null ) {
				ICFBamSchemaDefObj referencedObj = (ICFBamSchemaDefObj)javafxReferenceLookupDefSchema.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					String classCode = referencedObj.getClassCode();
					if( "SCHM".equals( classCode ) ) {
						form = javafxSchema.getSchemaDefFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFBamJavaFXSchemaDefPaneCommon spec = (ICFBamJavaFXSchemaDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFBamSchemaDefObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
	}

	protected class DefSchemaCFReferenceEditor
		extends CFReferenceEditor
	{
		public DefSchemaCFReferenceEditor() {
			super( new DefSchemaReferenceCallback() );
			setFieldName( "Defining Schema" );
		}
	}

	protected class TypeCFLabel
		extends CFLabel
	{
		public TypeCFLabel() {
			super();
			setText( "Type Specification" );
		}
	}

	protected class CallbackTypeChosen
	implements ICFBamJavaFXValueChosen
	{
		public CallbackTypeChosen() {
		}

		public void choseValue( ICFBamValueObj value ) {
			if( javafxReferenceLookupType != null ) {
				ICFBamParamObj cur = getJavaFXFocusAsParam();
				if( cur != null ) {
					ICFBamParamEditObj editObj = (ICFBamParamEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupType.setReferencedObject( value );
							editObj.setRequiredLookupType( value );
						}
					}
				}
			}
		}
	}

	protected class CompareValueByName
	implements Comparator<ICFBamValueObj>
	{
		public CompareValueByName() {
		}

		@Override
		public int compare(ICFBamValueObj o1, ICFBamValueObj o2) {
			if( o1 == null ) {
				if( o2 == null ) {
					return( 0 );
				}
				else {
					return( -1 );
				}
			}
			else if( o2 == null ) {
				return( 1 );
			}
			else {
				return( o1.getObjName().compareTo( o2.getObjName() ) );
			}
		}
	}

	protected class TypeReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			ICFBamParamObj focus = getJavaFXFocusAsParam();
			ICFBamParamEditObj editObj  = (ICFBamParamEditObj)focus.getEdit();
			if( editObj != null ) {
				focus = editObj;
			}
			ICFBamValueObj referencedObj = (ICFBamValueObj)javafxReferenceLookupType.getReferencedObject();
			List<ICFBamValueObj> listOfValue = null;
			ICFBamServerMethodObj refServerMeth = (ICFBamServerMethodObj)focus.getRequiredContainerServerMeth( javafxIsInitializing );
			if( refServerMeth == null ) {
				CFConsole.message( "You must specify a Containing Server Method before selecting a Type Specification" );
				return;
			}
			ICFBamTableObj refForTable = refServerMeth.getRequiredContainerForTable( javafxIsInitializing );
			if( refForTable == null ) {
				CFConsole.message( "You must specify a For Table before selecting a Type Specification" );
				return;
			}
			ICFBamSchemaDefObj refSchemaDef = refForTable.getRequiredContainerSchemaDef( javafxIsInitializing );
			if( refSchemaDef == null ) {
				CFConsole.message( "You must specify a Containing Schema Definition before selecting a Type Specification" );
				return;
			}
			listOfValue = refSchemaDef.getOptionalComponentsTypes( javafxIsInitializing );
			if( listOfValue == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"listOfValue" );
			}
			ICFBamValueObj[] arrValue = new ICFBamValueObj[listOfValue.size()];
			Iterator<ICFBamValueObj> iterValue = listOfValue.iterator();
			int idx = 0;
			while( iterValue.hasNext() ) {
				arrValue[idx++] = iterValue.next();
			}
			Arrays.sort( arrValue, new CompareValueByName() );
			Collection<ICFBamValueObj> cltn = new LinkedList<ICFBamValueObj>();
			for( idx = 0; idx < arrValue.length; idx++ ) {
				cltn.add( arrValue[idx] );
			}
			CFBorderPane form = javafxSchema.getValueFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackTypeChosen() );
			((ICFBamJavaFXValuePaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamParamObj focus = getJavaFXFocusAsParam();
			if( focus != null ) {
				ICFBamValueObj referencedObj = (ICFBamValueObj)javafxReferenceLookupType.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					String classCode = referencedObj.getClassCode();
					if( "VALU".equals( classCode ) ) {
						form = javafxSchema.getValueFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFBamJavaFXValuePaneCommon spec = (ICFBamJavaFXValuePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TBLC".equals( classCode ) ) {
						ICFBamTableColObj obj = (ICFBamTableColObj)referencedObj;
						form = javafxSchema.getTableColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTableColPaneCommon spec = (ICFBamJavaFXTableColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "ATOM".equals( classCode ) ) {
						ICFBamAtomObj obj = (ICFBamAtomObj)referencedObj;
						form = javafxSchema.getAtomFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXAtomPaneCommon spec = (ICFBamJavaFXAtomPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "BLBD".equals( classCode ) ) {
						ICFBamBlobDefObj obj = (ICFBamBlobDefObj)referencedObj;
						form = javafxSchema.getBlobDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXBlobDefPaneCommon spec = (ICFBamJavaFXBlobDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "BLBC".equals( classCode ) ) {
						ICFBamBlobColObj obj = (ICFBamBlobColObj)referencedObj;
						form = javafxSchema.getBlobColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXBlobColPaneCommon spec = (ICFBamJavaFXBlobColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "BLBT".equals( classCode ) ) {
						ICFBamBlobTypeObj obj = (ICFBamBlobTypeObj)referencedObj;
						form = javafxSchema.getBlobTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXBlobTypePaneCommon spec = (ICFBamJavaFXBlobTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "BOLD".equals( classCode ) ) {
						ICFBamBoolDefObj obj = (ICFBamBoolDefObj)referencedObj;
						form = javafxSchema.getBoolDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXBoolDefPaneCommon spec = (ICFBamJavaFXBoolDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "BOLC".equals( classCode ) ) {
						ICFBamBoolColObj obj = (ICFBamBoolColObj)referencedObj;
						form = javafxSchema.getBoolColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXBoolColPaneCommon spec = (ICFBamJavaFXBoolColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "BOLT".equals( classCode ) ) {
						ICFBamBoolTypeObj obj = (ICFBamBoolTypeObj)referencedObj;
						form = javafxSchema.getBoolTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXBoolTypePaneCommon spec = (ICFBamJavaFXBoolTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "I16D".equals( classCode ) ) {
						ICFBamInt16DefObj obj = (ICFBamInt16DefObj)referencedObj;
						form = javafxSchema.getInt16DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt16DefPaneCommon spec = (ICFBamJavaFXInt16DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "I16C".equals( classCode ) ) {
						ICFBamInt16ColObj obj = (ICFBamInt16ColObj)referencedObj;
						form = javafxSchema.getInt16ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt16ColPaneCommon spec = (ICFBamJavaFXInt16ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "I16T".equals( classCode ) ) {
						ICFBamInt16TypeObj obj = (ICFBamInt16TypeObj)referencedObj;
						form = javafxSchema.getInt16TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt16TypePaneCommon spec = (ICFBamJavaFXInt16TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "IG16".equals( classCode ) ) {
						ICFBamId16GenObj obj = (ICFBamId16GenObj)referencedObj;
						form = javafxSchema.getId16GenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXId16GenPaneCommon spec = (ICFBamJavaFXId16GenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "ENMD".equals( classCode ) ) {
						ICFBamEnumDefObj obj = (ICFBamEnumDefObj)referencedObj;
						form = javafxSchema.getEnumDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXEnumDefPaneCommon spec = (ICFBamJavaFXEnumDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "ENMT".equals( classCode ) ) {
						ICFBamEnumTypeObj obj = (ICFBamEnumTypeObj)referencedObj;
						form = javafxSchema.getEnumTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXEnumTypePaneCommon spec = (ICFBamJavaFXEnumTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "I32D".equals( classCode ) ) {
						ICFBamInt32DefObj obj = (ICFBamInt32DefObj)referencedObj;
						form = javafxSchema.getInt32DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt32DefPaneCommon spec = (ICFBamJavaFXInt32DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "I32C".equals( classCode ) ) {
						ICFBamInt32ColObj obj = (ICFBamInt32ColObj)referencedObj;
						form = javafxSchema.getInt32ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt32ColPaneCommon spec = (ICFBamJavaFXInt32ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "I32T".equals( classCode ) ) {
						ICFBamInt32TypeObj obj = (ICFBamInt32TypeObj)referencedObj;
						form = javafxSchema.getInt32TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt32TypePaneCommon spec = (ICFBamJavaFXInt32TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "IG32".equals( classCode ) ) {
						ICFBamId32GenObj obj = (ICFBamId32GenObj)referencedObj;
						form = javafxSchema.getId32GenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXId32GenPaneCommon spec = (ICFBamJavaFXId32GenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "I64D".equals( classCode ) ) {
						ICFBamInt64DefObj obj = (ICFBamInt64DefObj)referencedObj;
						form = javafxSchema.getInt64DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt64DefPaneCommon spec = (ICFBamJavaFXInt64DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "I64C".equals( classCode ) ) {
						ICFBamInt64ColObj obj = (ICFBamInt64ColObj)referencedObj;
						form = javafxSchema.getInt64ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt64ColPaneCommon spec = (ICFBamJavaFXInt64ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "I64T".equals( classCode ) ) {
						ICFBamInt64TypeObj obj = (ICFBamInt64TypeObj)referencedObj;
						form = javafxSchema.getInt64TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt64TypePaneCommon spec = (ICFBamJavaFXInt64TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "IG64".equals( classCode ) ) {
						ICFBamId64GenObj obj = (ICFBamId64GenObj)referencedObj;
						form = javafxSchema.getId64GenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXId64GenPaneCommon spec = (ICFBamJavaFXId64GenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "U16D".equals( classCode ) ) {
						ICFBamUInt16DefObj obj = (ICFBamUInt16DefObj)referencedObj;
						form = javafxSchema.getUInt16DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt16DefPaneCommon spec = (ICFBamJavaFXUInt16DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "U16C".equals( classCode ) ) {
						ICFBamUInt16ColObj obj = (ICFBamUInt16ColObj)referencedObj;
						form = javafxSchema.getUInt16ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt16ColPaneCommon spec = (ICFBamJavaFXUInt16ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "U16T".equals( classCode ) ) {
						ICFBamUInt16TypeObj obj = (ICFBamUInt16TypeObj)referencedObj;
						form = javafxSchema.getUInt16TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt16TypePaneCommon spec = (ICFBamJavaFXUInt16TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "U32D".equals( classCode ) ) {
						ICFBamUInt32DefObj obj = (ICFBamUInt32DefObj)referencedObj;
						form = javafxSchema.getUInt32DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt32DefPaneCommon spec = (ICFBamJavaFXUInt32DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "U32C".equals( classCode ) ) {
						ICFBamUInt32ColObj obj = (ICFBamUInt32ColObj)referencedObj;
						form = javafxSchema.getUInt32ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt32ColPaneCommon spec = (ICFBamJavaFXUInt32ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "U32T".equals( classCode ) ) {
						ICFBamUInt32TypeObj obj = (ICFBamUInt32TypeObj)referencedObj;
						form = javafxSchema.getUInt32TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt32TypePaneCommon spec = (ICFBamJavaFXUInt32TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "U64D".equals( classCode ) ) {
						ICFBamUInt64DefObj obj = (ICFBamUInt64DefObj)referencedObj;
						form = javafxSchema.getUInt64DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt64DefPaneCommon spec = (ICFBamJavaFXUInt64DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "U64C".equals( classCode ) ) {
						ICFBamUInt64ColObj obj = (ICFBamUInt64ColObj)referencedObj;
						form = javafxSchema.getUInt64ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt64ColPaneCommon spec = (ICFBamJavaFXUInt64ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "U64T".equals( classCode ) ) {
						ICFBamUInt64TypeObj obj = (ICFBamUInt64TypeObj)referencedObj;
						form = javafxSchema.getUInt64TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt64TypePaneCommon spec = (ICFBamJavaFXUInt64TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "FLTD".equals( classCode ) ) {
						ICFBamFloatDefObj obj = (ICFBamFloatDefObj)referencedObj;
						form = javafxSchema.getFloatDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXFloatDefPaneCommon spec = (ICFBamJavaFXFloatDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "FLTC".equals( classCode ) ) {
						ICFBamFloatColObj obj = (ICFBamFloatColObj)referencedObj;
						form = javafxSchema.getFloatColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXFloatColPaneCommon spec = (ICFBamJavaFXFloatColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "FLTT".equals( classCode ) ) {
						ICFBamFloatTypeObj obj = (ICFBamFloatTypeObj)referencedObj;
						form = javafxSchema.getFloatTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXFloatTypePaneCommon spec = (ICFBamJavaFXFloatTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "DBLD".equals( classCode ) ) {
						ICFBamDoubleDefObj obj = (ICFBamDoubleDefObj)referencedObj;
						form = javafxSchema.getDoubleDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDoubleDefPaneCommon spec = (ICFBamJavaFXDoubleDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "DBLC".equals( classCode ) ) {
						ICFBamDoubleColObj obj = (ICFBamDoubleColObj)referencedObj;
						form = javafxSchema.getDoubleColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDoubleColPaneCommon spec = (ICFBamJavaFXDoubleColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "DBLT".equals( classCode ) ) {
						ICFBamDoubleTypeObj obj = (ICFBamDoubleTypeObj)referencedObj;
						form = javafxSchema.getDoubleTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDoubleTypePaneCommon spec = (ICFBamJavaFXDoubleTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "NUMD".equals( classCode ) ) {
						ICFBamNumberDefObj obj = (ICFBamNumberDefObj)referencedObj;
						form = javafxSchema.getNumberDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNumberDefPaneCommon spec = (ICFBamJavaFXNumberDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "NUMC".equals( classCode ) ) {
						ICFBamNumberColObj obj = (ICFBamNumberColObj)referencedObj;
						form = javafxSchema.getNumberColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNumberColPaneCommon spec = (ICFBamJavaFXNumberColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "NUMT".equals( classCode ) ) {
						ICFBamNumberTypeObj obj = (ICFBamNumberTypeObj)referencedObj;
						form = javafxSchema.getNumberTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNumberTypePaneCommon spec = (ICFBamJavaFXNumberTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "STRD".equals( classCode ) ) {
						ICFBamStringDefObj obj = (ICFBamStringDefObj)referencedObj;
						form = javafxSchema.getStringDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXStringDefPaneCommon spec = (ICFBamJavaFXStringDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "STRC".equals( classCode ) ) {
						ICFBamStringColObj obj = (ICFBamStringColObj)referencedObj;
						form = javafxSchema.getStringColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXStringColPaneCommon spec = (ICFBamJavaFXStringColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "STRT".equals( classCode ) ) {
						ICFBamStringTypeObj obj = (ICFBamStringTypeObj)referencedObj;
						form = javafxSchema.getStringTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXStringTypePaneCommon spec = (ICFBamJavaFXStringTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TXTD".equals( classCode ) ) {
						ICFBamTextDefObj obj = (ICFBamTextDefObj)referencedObj;
						form = javafxSchema.getTextDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTextDefPaneCommon spec = (ICFBamJavaFXTextDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TXTC".equals( classCode ) ) {
						ICFBamTextColObj obj = (ICFBamTextColObj)referencedObj;
						form = javafxSchema.getTextColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTextColPaneCommon spec = (ICFBamJavaFXTextColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TXTT".equals( classCode ) ) {
						ICFBamTextTypeObj obj = (ICFBamTextTypeObj)referencedObj;
						form = javafxSchema.getTextTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTextTypePaneCommon spec = (ICFBamJavaFXTextTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "NTKD".equals( classCode ) ) {
						ICFBamNmTokenDefObj obj = (ICFBamNmTokenDefObj)referencedObj;
						form = javafxSchema.getNmTokenDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNmTokenDefPaneCommon spec = (ICFBamJavaFXNmTokenDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "NTKC".equals( classCode ) ) {
						ICFBamNmTokenColObj obj = (ICFBamNmTokenColObj)referencedObj;
						form = javafxSchema.getNmTokenColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNmTokenColPaneCommon spec = (ICFBamJavaFXNmTokenColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "NTKT".equals( classCode ) ) {
						ICFBamNmTokenTypeObj obj = (ICFBamNmTokenTypeObj)referencedObj;
						form = javafxSchema.getNmTokenTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNmTokenTypePaneCommon spec = (ICFBamJavaFXNmTokenTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "NTSD".equals( classCode ) ) {
						ICFBamNmTokensDefObj obj = (ICFBamNmTokensDefObj)referencedObj;
						form = javafxSchema.getNmTokensDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNmTokensDefPaneCommon spec = (ICFBamJavaFXNmTokensDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "NTSC".equals( classCode ) ) {
						ICFBamNmTokensColObj obj = (ICFBamNmTokensColObj)referencedObj;
						form = javafxSchema.getNmTokensColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNmTokensColPaneCommon spec = (ICFBamJavaFXNmTokensColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "NTST".equals( classCode ) ) {
						ICFBamNmTokensTypeObj obj = (ICFBamNmTokensTypeObj)referencedObj;
						form = javafxSchema.getNmTokensTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNmTokensTypePaneCommon spec = (ICFBamJavaFXNmTokensTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TKND".equals( classCode ) ) {
						ICFBamTokenDefObj obj = (ICFBamTokenDefObj)referencedObj;
						form = javafxSchema.getTokenDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTokenDefPaneCommon spec = (ICFBamJavaFXTokenDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TKNC".equals( classCode ) ) {
						ICFBamTokenColObj obj = (ICFBamTokenColObj)referencedObj;
						form = javafxSchema.getTokenColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTokenColPaneCommon spec = (ICFBamJavaFXTokenColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TKNT".equals( classCode ) ) {
						ICFBamTokenTypeObj obj = (ICFBamTokenTypeObj)referencedObj;
						form = javafxSchema.getTokenTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTokenTypePaneCommon spec = (ICFBamJavaFXTokenTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "DATD".equals( classCode ) ) {
						ICFBamDateDefObj obj = (ICFBamDateDefObj)referencedObj;
						form = javafxSchema.getDateDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDateDefPaneCommon spec = (ICFBamJavaFXDateDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "DATC".equals( classCode ) ) {
						ICFBamDateColObj obj = (ICFBamDateColObj)referencedObj;
						form = javafxSchema.getDateColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDateColPaneCommon spec = (ICFBamJavaFXDateColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "DATT".equals( classCode ) ) {
						ICFBamDateTypeObj obj = (ICFBamDateTypeObj)referencedObj;
						form = javafxSchema.getDateTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDateTypePaneCommon spec = (ICFBamJavaFXDateTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TIMD".equals( classCode ) ) {
						ICFBamTimeDefObj obj = (ICFBamTimeDefObj)referencedObj;
						form = javafxSchema.getTimeDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTimeDefPaneCommon spec = (ICFBamJavaFXTimeDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TIMC".equals( classCode ) ) {
						ICFBamTimeColObj obj = (ICFBamTimeColObj)referencedObj;
						form = javafxSchema.getTimeColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTimeColPaneCommon spec = (ICFBamJavaFXTimeColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TIMT".equals( classCode ) ) {
						ICFBamTimeTypeObj obj = (ICFBamTimeTypeObj)referencedObj;
						form = javafxSchema.getTimeTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTimeTypePaneCommon spec = (ICFBamJavaFXTimeTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TSPD".equals( classCode ) ) {
						ICFBamTimestampDefObj obj = (ICFBamTimestampDefObj)referencedObj;
						form = javafxSchema.getTimestampDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTimestampDefPaneCommon spec = (ICFBamJavaFXTimestampDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TSPC".equals( classCode ) ) {
						ICFBamTimestampColObj obj = (ICFBamTimestampColObj)referencedObj;
						form = javafxSchema.getTimestampColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTimestampColPaneCommon spec = (ICFBamJavaFXTimestampColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TSPT".equals( classCode ) ) {
						ICFBamTimestampTypeObj obj = (ICFBamTimestampTypeObj)referencedObj;
						form = javafxSchema.getTimestampTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTimestampTypePaneCommon spec = (ICFBamJavaFXTimestampTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "DAZD".equals( classCode ) ) {
						ICFBamTZDateDefObj obj = (ICFBamTZDateDefObj)referencedObj;
						form = javafxSchema.getTZDateDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZDateDefPaneCommon spec = (ICFBamJavaFXTZDateDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "DAZC".equals( classCode ) ) {
						ICFBamTZDateColObj obj = (ICFBamTZDateColObj)referencedObj;
						form = javafxSchema.getTZDateColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZDateColPaneCommon spec = (ICFBamJavaFXTZDateColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "DAZT".equals( classCode ) ) {
						ICFBamTZDateTypeObj obj = (ICFBamTZDateTypeObj)referencedObj;
						form = javafxSchema.getTZDateTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZDateTypePaneCommon spec = (ICFBamJavaFXTZDateTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TMZD".equals( classCode ) ) {
						ICFBamTZTimeDefObj obj = (ICFBamTZTimeDefObj)referencedObj;
						form = javafxSchema.getTZTimeDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZTimeDefPaneCommon spec = (ICFBamJavaFXTZTimeDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TMZC".equals( classCode ) ) {
						ICFBamTZTimeColObj obj = (ICFBamTZTimeColObj)referencedObj;
						form = javafxSchema.getTZTimeColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZTimeColPaneCommon spec = (ICFBamJavaFXTZTimeColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "TMZT".equals( classCode ) ) {
						ICFBamTZTimeTypeObj obj = (ICFBamTZTimeTypeObj)referencedObj;
						form = javafxSchema.getTZTimeTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZTimeTypePaneCommon spec = (ICFBamJavaFXTZTimeTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "ZSTD".equals( classCode ) ) {
						ICFBamTZTimestampDefObj obj = (ICFBamTZTimestampDefObj)referencedObj;
						form = javafxSchema.getTZTimestampDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZTimestampDefPaneCommon spec = (ICFBamJavaFXTZTimestampDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "ZSTC".equals( classCode ) ) {
						ICFBamTZTimestampColObj obj = (ICFBamTZTimestampColObj)referencedObj;
						form = javafxSchema.getTZTimestampColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZTimestampColPaneCommon spec = (ICFBamJavaFXTZTimestampColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "ZSTT".equals( classCode ) ) {
						ICFBamTZTimestampTypeObj obj = (ICFBamTZTimestampTypeObj)referencedObj;
						form = javafxSchema.getTZTimestampTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZTimestampTypePaneCommon spec = (ICFBamJavaFXTZTimestampTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "UIDD".equals( classCode ) ) {
						ICFBamUuidDefObj obj = (ICFBamUuidDefObj)referencedObj;
						form = javafxSchema.getUuidDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUuidDefPaneCommon spec = (ICFBamJavaFXUuidDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "UIDC".equals( classCode ) ) {
						ICFBamUuidColObj obj = (ICFBamUuidColObj)referencedObj;
						form = javafxSchema.getUuidColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUuidColPaneCommon spec = (ICFBamJavaFXUuidColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "UIDT".equals( classCode ) ) {
						ICFBamUuidTypeObj obj = (ICFBamUuidTypeObj)referencedObj;
						form = javafxSchema.getUuidTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUuidTypePaneCommon spec = (ICFBamJavaFXUuidTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( "IGUU".equals( classCode ) ) {
						ICFBamUuidGenObj obj = (ICFBamUuidGenObj)referencedObj;
						form = javafxSchema.getUuidGenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUuidGenPaneCommon spec = (ICFBamJavaFXUuidGenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFBamValueObj, ICFBamTableColObj, ICFBamAtomObj, ICFBamBlobDefObj, ICFBamBlobColObj, ICFBamBlobTypeObj, ICFBamBoolDefObj, ICFBamBoolColObj, ICFBamBoolTypeObj, ICFBamInt16DefObj, ICFBamInt16ColObj, ICFBamInt16TypeObj, ICFBamId16GenObj, ICFBamEnumDefObj, ICFBamEnumTypeObj, ICFBamInt32DefObj, ICFBamInt32ColObj, ICFBamInt32TypeObj, ICFBamId32GenObj, ICFBamInt64DefObj, ICFBamInt64ColObj, ICFBamInt64TypeObj, ICFBamId64GenObj, ICFBamUInt16DefObj, ICFBamUInt16ColObj, ICFBamUInt16TypeObj, ICFBamUInt32DefObj, ICFBamUInt32ColObj, ICFBamUInt32TypeObj, ICFBamUInt64DefObj, ICFBamUInt64ColObj, ICFBamUInt64TypeObj, ICFBamFloatDefObj, ICFBamFloatColObj, ICFBamFloatTypeObj, ICFBamDoubleDefObj, ICFBamDoubleColObj, ICFBamDoubleTypeObj, ICFBamNumberDefObj, ICFBamNumberColObj, ICFBamNumberTypeObj, ICFBamStringDefObj, ICFBamStringColObj, ICFBamStringTypeObj, ICFBamTextDefObj, ICFBamTextColObj, ICFBamTextTypeObj, ICFBamNmTokenDefObj, ICFBamNmTokenColObj, ICFBamNmTokenTypeObj, ICFBamNmTokensDefObj, ICFBamNmTokensColObj, ICFBamNmTokensTypeObj, ICFBamTokenDefObj, ICFBamTokenColObj, ICFBamTokenTypeObj, ICFBamDateDefObj, ICFBamDateColObj, ICFBamDateTypeObj, ICFBamTimeDefObj, ICFBamTimeColObj, ICFBamTimeTypeObj, ICFBamTimestampDefObj, ICFBamTimestampColObj, ICFBamTimestampTypeObj, ICFBamTZDateDefObj, ICFBamTZDateColObj, ICFBamTZDateTypeObj, ICFBamTZTimeDefObj, ICFBamTZTimeColObj, ICFBamTZTimeTypeObj, ICFBamTZTimestampDefObj, ICFBamTZTimestampColObj, ICFBamTZTimestampTypeObj, ICFBamUuidDefObj, ICFBamUuidColObj, ICFBamUuidTypeObj, ICFBamUuidGenObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
}

	protected class TypeCFReferenceEditor
		extends CFReferenceEditor
	{
		public TypeCFReferenceEditor() {
			super( new TypeReferenceCallback() );
			setFieldName( "Type Specification" );
		}
	}

	protected class IdCFLabel
		extends CFLabel
	{
		public IdCFLabel() {
			super();
			setText( "Id" );
		}
	}

	protected class IdEditor
		extends CFInt64Editor
	{
		public IdEditor() {
			super();
			setMinValue( CFBamParamBuff.ID_MIN_VALUE );
			setFieldName( "Id" );
		}
	}

	protected class NameCFLabel
		extends CFLabel
	{
		public NameCFLabel() {
			super();
			setText( "Name" );
		}
	}

	protected class NameEditor
		extends CFStringEditor
	{
		public NameEditor() {
			super();
			setMaxLen( 192 );
			setFieldName( "Name" );
		}
	}

	protected class ShortDescriptionCFLabel
		extends CFLabel
	{
		public ShortDescriptionCFLabel() {
			super();
			setText( "Short Description" );
		}
	}

	protected class ShortDescriptionEditor
		extends CFStringEditor
	{
		public ShortDescriptionEditor() {
			super();
			setMaxLen( 128 );
			setFieldName( "Short Description" );
		}
	}

	protected class DescriptionCFLabel
		extends CFLabel
	{
		public DescriptionCFLabel() {
			super();
			setText( "Description" );
		}
	}

	protected class DescriptionEditor
		extends CFStringEditor
	{
		public DescriptionEditor() {
			super();
			setMaxLen( 1023 );
			setFieldName( "Description" );
		}
	}

	protected class IsNullableCFLabel
		extends CFLabel
	{
		public IsNullableCFLabel() {
			super();
			setText( "Is Nullable" );
		}
	}

	protected class IsNullableEditor
		extends CFBoolEditor
	{
		public IsNullableEditor() {
			super();
			setIsNullable( false );
			setFieldName( "Is Nullable" );
		}
	}

	protected ICFBamSchemaDefObj javafxLookupDefSchemaObj = null;
	protected DefSchemaCFLabel javafxLabelLookupDefSchema = null;
	protected DefSchemaCFReferenceEditor javafxReferenceLookupDefSchema = null;
	protected ICFBamValueObj javafxLookupTypeObj = null;
	protected TypeCFLabel javafxLabelLookupType = null;
	protected TypeCFReferenceEditor javafxReferenceLookupType = null;
	protected IdCFLabel javafxLabelId = null;
	protected IdEditor javafxEditorId = null;
	protected NameCFLabel javafxLabelName = null;
	protected NameEditor javafxEditorName = null;
	protected ShortDescriptionCFLabel javafxLabelShortDescription = null;
	protected ShortDescriptionEditor javafxEditorShortDescription = null;
	protected DescriptionCFLabel javafxLabelDescription = null;
	protected DescriptionEditor javafxEditorDescription = null;
	protected IsNullableCFLabel javafxLabelIsNullable = null;
	protected IsNullableEditor javafxEditorIsNullable = null;

	public CFBamCustEditorParamAttrPane( ICFFormManager formManager, ICFBamJavaFXSchema argSchema, ICFBamParamObj argFocus ) {
		super();
		Control ctrl;
		CFLabel label;
		CFReferenceEditor reference;
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
		setJavaFXFocusAsParam( argFocus );
		setPadding( new Insets(5) );
		setHgap( 5 );
		setVgap( 5 );
		setAlignment( Pos.CENTER );
		ColumnConstraints column1 = new ColumnConstraints( 125 );
		ColumnConstraints column2 = new ColumnConstraints( 125, 300, 500 );
		column2.setHgrow( Priority.ALWAYS );
		getColumnConstraints().addAll( column1, column2 );
		int gridRow = 0;

		label = getJavaFXLabelId();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorId();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelLookupDefSchema();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		reference = getJavaFXReferenceLookupDefSchema();
		setHalignment( reference, HPos.LEFT );
		add( reference, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelName();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorName();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelDescription();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorDescription();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelShortDescription();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorShortDescription();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelLookupType();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		reference = getJavaFXReferenceLookupType();
		setHalignment( reference, HPos.LEFT );
		add( reference, 1, gridRow );

		gridRow ++;

		label = getJavaFXLabelIsNullable();
		setHalignment( label, HPos.LEFT );
		add( label, 0, gridRow );

		ctrl = getJavaFXEditorIsNullable();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 1, gridRow );

		gridRow ++;

		populateFields();
		adjustComponentEnableStates();
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
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFBamParamObj getJavaFXFocusAsParam() {
		return( (ICFBamParamObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsParam( ICFBamParamObj value ) {
		setJavaFXFocus( value );
	}

	public ICFBamSchemaDefObj getJavaFXLookupDefSchemaObj() {
		return( javafxLookupDefSchemaObj );
	}

	public void setJavaFXLookupDefSchemaObj( ICFBamSchemaDefObj value ) {
		javafxLookupDefSchemaObj = value;
	}

	public CFLabel getJavaFXLabelLookupDefSchema() {
		if( javafxLabelLookupDefSchema == null ) {
			javafxLabelLookupDefSchema = new DefSchemaCFLabel();
		}
		return( javafxLabelLookupDefSchema );
	}

	public CFReferenceEditor getJavaFXReferenceLookupDefSchema() {
		if( javafxReferenceLookupDefSchema == null ) {
			javafxReferenceLookupDefSchema = new DefSchemaCFReferenceEditor();
		}
		return( javafxReferenceLookupDefSchema );
	}

	public void setJavaFXReferenceLookupDefSchema( DefSchemaCFReferenceEditor value ) {
		javafxReferenceLookupDefSchema = value;
	}

	public ICFBamValueObj getJavaFXLookupTypeObj() {
		return( javafxLookupTypeObj );
	}

	public void setJavaFXLookupTypeObj( ICFBamValueObj value ) {
		javafxLookupTypeObj = value;
	}

	public CFLabel getJavaFXLabelLookupType() {
		if( javafxLabelLookupType == null ) {
			javafxLabelLookupType = new TypeCFLabel();
		}
		return( javafxLabelLookupType );
	}

	public CFReferenceEditor getJavaFXReferenceLookupType() {
		if( javafxReferenceLookupType == null ) {
			javafxReferenceLookupType = new TypeCFReferenceEditor();
		}
		return( javafxReferenceLookupType );
	}

	public void setJavaFXReferenceLookupType( TypeCFReferenceEditor value ) {
		javafxReferenceLookupType = value;
	}

	public IdCFLabel getJavaFXLabelId() {
		if( javafxLabelId == null ) {
			javafxLabelId = new IdCFLabel();
		}
		return( javafxLabelId );
	}

	public void setJavaFXLabelId( IdCFLabel value ) {
		javafxLabelId = value;
	}

	public IdEditor getJavaFXEditorId() {
		if( javafxEditorId == null ) {
			javafxEditorId = new IdEditor();
		}
		return( javafxEditorId );
	}

	public void setJavaFXEditorId( IdEditor value ) {
		javafxEditorId = value;
	}

	public NameCFLabel getJavaFXLabelName() {
		if( javafxLabelName == null ) {
			javafxLabelName = new NameCFLabel();
		}
		return( javafxLabelName );
	}

	public void setJavaFXLabelName( NameCFLabel value ) {
		javafxLabelName = value;
	}

	public NameEditor getJavaFXEditorName() {
		if( javafxEditorName == null ) {
			javafxEditorName = new NameEditor();
		}
		return( javafxEditorName );
	}

	public void setJavaFXEditorName( NameEditor value ) {
		javafxEditorName = value;
	}

	public ShortDescriptionCFLabel getJavaFXLabelShortDescription() {
		if( javafxLabelShortDescription == null ) {
			javafxLabelShortDescription = new ShortDescriptionCFLabel();
		}
		return( javafxLabelShortDescription );
	}

	public void setJavaFXLabelShortDescription( ShortDescriptionCFLabel value ) {
		javafxLabelShortDescription = value;
	}

	public ShortDescriptionEditor getJavaFXEditorShortDescription() {
		if( javafxEditorShortDescription == null ) {
			javafxEditorShortDescription = new ShortDescriptionEditor();
		}
		return( javafxEditorShortDescription );
	}

	public void setJavaFXEditorShortDescription( ShortDescriptionEditor value ) {
		javafxEditorShortDescription = value;
	}

	public DescriptionCFLabel getJavaFXLabelDescription() {
		if( javafxLabelDescription == null ) {
			javafxLabelDescription = new DescriptionCFLabel();
		}
		return( javafxLabelDescription );
	}

	public void setJavaFXLabelDescription( DescriptionCFLabel value ) {
		javafxLabelDescription = value;
	}

	public DescriptionEditor getJavaFXEditorDescription() {
		if( javafxEditorDescription == null ) {
			javafxEditorDescription = new DescriptionEditor();
		}
		return( javafxEditorDescription );
	}

	public void setJavaFXEditorDescription( DescriptionEditor value ) {
		javafxEditorDescription = value;
	}

	public IsNullableCFLabel getJavaFXLabelIsNullable() {
		if( javafxLabelIsNullable == null ) {
			javafxLabelIsNullable = new IsNullableCFLabel();
		}
		return( javafxLabelIsNullable );
	}

	public void setJavaFXLabelIsNullable( IsNullableCFLabel value ) {
		javafxLabelIsNullable = value;
	}

	public IsNullableEditor getJavaFXEditorIsNullable() {
		if( javafxEditorIsNullable == null ) {
			javafxEditorIsNullable = new IsNullableEditor();
		}
		return( javafxEditorIsNullable );
	}

	public void setJavaFXEditorIsNullable( IsNullableEditor value ) {
		javafxEditorIsNullable = value;
	}

	public void populateFields()
	{
		ICFBamParamObj popObj;
		ICFBamParamObj focus = getJavaFXFocusAsParam();
		if( focus != null ) {
			popObj = (ICFBamParamObj)(focus.getEdit());
			if( popObj == null ) {
				popObj = focus;
			}
		}
		else {
			popObj = null;
		}
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			popObj = null;
		}
		if( popObj == null ) {
			javafxLookupDefSchemaObj = null;
		}
		else {
			javafxLookupDefSchemaObj = (ICFBamSchemaDefObj)popObj.getOptionalLookupDefSchema( javafxIsInitializing );
		}
		if( javafxReferenceLookupDefSchema != null ) {
			javafxReferenceLookupDefSchema.setReferencedObject( javafxLookupDefSchemaObj );
		}

		if( popObj == null ) {
			javafxLookupTypeObj = null;
		}
		else {
			javafxLookupTypeObj = (ICFBamValueObj)popObj.getRequiredLookupType( javafxIsInitializing );
		}
		if( javafxReferenceLookupType != null ) {
			javafxReferenceLookupType.setReferencedObject( javafxLookupTypeObj );
		}

		if( popObj == null ) {
			getJavaFXEditorId().setInt64Value( null );
		}
		else {
			getJavaFXEditorId().setInt64Value( popObj.getRequiredId() );
		}

		if( popObj == null ) {
			getJavaFXEditorName().setStringValue( null );
		}
		else {
			getJavaFXEditorName().setStringValue( popObj.getRequiredName() );
		}

		if( popObj == null ) {
			getJavaFXEditorShortDescription().setStringValue( null );
		}
		else {
			getJavaFXEditorShortDescription().setStringValue( popObj.getOptionalShortDescription() );
		}

		if( popObj == null ) {
			getJavaFXEditorDescription().setStringValue( null );
		}
		else {
			getJavaFXEditorDescription().setStringValue( popObj.getOptionalDescription() );
		}

		if( popObj == null ) {
			getJavaFXEditorIsNullable().setBooleanValue( null );
		}
		else {
			getJavaFXEditorIsNullable().setBooleanValue( popObj.getRequiredIsNullable() );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFBamParamObj focus = getJavaFXFocusAsParam();
		ICFBamParamEditObj editObj;
		if( focus != null ) {
			editObj = (ICFBamParamEditObj)(focus.getEdit());
		}
		else {
			editObj = null;
		}
		if( editObj == null ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Pane is unfocused or is not editing the focus object" );
		}

		javafxLookupDefSchemaObj = (ICFBamSchemaDefObj)( javafxReferenceLookupDefSchema.getReferencedObject() );
		editObj.setOptionalLookupDefSchema( javafxLookupDefSchemaObj );

		javafxLookupTypeObj = (ICFBamValueObj)( javafxReferenceLookupType.getReferencedObject() );
		editObj.setRequiredLookupType( javafxLookupTypeObj );

		editObj.setRequiredName( getJavaFXEditorName().getStringValue() );

		editObj.setOptionalShortDescription( getJavaFXEditorShortDescription().getStringValue() );

		editObj.setOptionalDescription( getJavaFXEditorDescription().getStringValue() );

		editObj.setRequiredIsNullable( getJavaFXEditorIsNullable().getBooleanValue() );

		if( ( editObj.getRequiredName() == null ) || ( editObj.getRequiredName().length() <= 0 ) ) {
			throw new CFLibEmptyArgumentException( getClass(),
				S_ProcName,
				0,
				"Name" );
		}
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFBamParamObj focus = getJavaFXFocusAsParam();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFBamParamEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFBamParamEditObj)focus.getEdit();
		}
		else {
			editObj = null;
		}
		switch( value ) {
			case Unknown:
				switch( oldValue ) {
					case Unknown:
						break;
					default:
						if( editObj != null ) {
							editObj.endEdit();
						}
						break;
				}
				break;
			case Add:
				switch( oldValue ) {
					case Unknown:
					case Add:
					case View:
						if( editObj == null ) {
							if( focus != null ) {
								if( ! focus.getIsNew() ) {
									throw new CFLibUsageException( getClass(),
										S_ProcName,
										"Transitioning to PaneMode Add requires Focus.getIsNew() to be true" );
								}
								editObj = (ICFBamParamEditObj)focus.beginEdit();
								if( editObj == null ) {
									throw new CFLibUsageException( getClass(),
										S_ProcName,
										"Expected beginEdit() to return a new edition of the focus object" );
								}
							}
							else {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"focus" );
							}
						}
						break;
					case Edit:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"Cannot transition PaneMode Edit to Add" );
					case Update:
						if( ( editObj == null ) || ( ! editObj.getIsNew() ) ) {
							throw new CFLibUsageException( getClass(),
								S_ProcName,
								"Cannot transition PaneMode Update to Add" );
						}
						break;
					case Delete:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"Cannot transition PaneMode Delete to Add" );
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"Cannot transition PaneMode default to Add" );
				}
				break;
			case View:
				switch( oldValue ) {
					case Unknown:
						break;
					case View:
						break;
					case Edit:
						break;
					case Update:
						break;
					case Delete:
						break;
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"Cannot transition PaneMode " + oldValue + " to View" );
				}
				if( editObj != null ) {
					editObj.endEdit();
				}
				break;
			case Edit:
				switch( oldValue ) {
					case Unknown:
						if( editObj == null ) {
							editObj = (ICFBamParamEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					case View:
						if( editObj == null ) {
							editObj = (ICFBamParamEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					case Edit:
						if( editObj == null ) {
							editObj = (ICFBamParamEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					case Update:
						if( editObj == null ) {
							throw new CFLibUsageException( getClass(),
								S_ProcName,
								"Cannot transition PaneMode " + oldValue + " to Edit" ); 
						}
						break;
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"Cannot transition PaneMode " + oldValue + " to Edit" ); 
				}
				break;
			case Update:
				if( ( oldValue != CFPane.PaneMode.Edit ) && ( oldValue != CFPane.PaneMode.Add ) ) {
					throw new CFLibUsageException( getClass(),
						S_ProcName,
						"Cannot transition from mode " + oldValue + " to Update" );
				}
				super.setPaneMode( value );
				if( editObj != null ) {
					postFields();
					if( editObj.getIsNew() ) {
						focus = (ICFBamParamObj)editObj.create();
						setJavaFXFocus( focus );
					}
					else {
						editObj.update();
					}
					editObj = null;
				}
				setPaneMode( CFPane.PaneMode.View );
				break;
			case Delete:
				switch( oldValue ) {
					case View:
						if( focus != null ) {
							if( editObj == null ) {
								editObj = (ICFBamParamEditObj)focus.beginEdit();
								if( editObj == null ) {
									throw new CFLibUsageException( getClass(),
										S_ProcName,
										"Expected beginEdit() to return a new edition of the focus object" );
								}
							}
						}
						break;
					case Edit:
						if( focus != null ) {
							if( editObj == null ) {
								editObj = (ICFBamParamEditObj)focus.beginEdit();
								if( editObj == null ) {
									throw new CFLibUsageException( getClass(),
										S_ProcName,
										"Expected beginEdit() to return a new edition of the focus object" );
								}
							}
						}
						break;
					case Update:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"Cannot transition PaneMode Update to Delete" ); 
					case Delete:
						if( editObj == null ) {
							editObj = (ICFBamParamEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									"Expected beginEdit() to return a new edition of the focus object" );
							}
						}
						break;
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							"Cannot transition PaneMode " + oldValue + " to Delete" ); 
				}
				editObj.deleteInstance();
				editObj = null;
				setJavaFXFocus( null );
				setPaneMode( CFPane.PaneMode.Unknown );
				break;
			default:
				switch( oldValue ) {
					case Unknown:
						break;
					default:
						if( editObj != null ) {
							editObj.endEdit();
						}
						break;
				}
				break;
		}
		super.setPaneMode( value );
		populateFields();
		adjustComponentEnableStates();
	}

	public void adjustComponentEnableStates() {
		CFPane.PaneMode mode = getPaneMode();
		boolean isEditing;
		switch( mode ) {
			case Unknown:
			case View:
			case Delete:
				isEditing = false;
				break;
			case Add:
			case Edit:
			case Update:
				isEditing = true;
				break;
			default:
				isEditing = false;
				break;
		}
		if( isEditing ) {
			ICFBamParamObj focus = getJavaFXFocusAsParam();
			if( focus == null ) {
				isEditing = false;
			}
			else if( null == focus.getEdit() ) {
				isEditing = false;
			}
		}
		if( javafxReferenceLookupDefSchema != null ) {
			javafxReferenceLookupDefSchema.setCustomDisable( true );
		}
		if( javafxReferenceLookupType != null ) {
			javafxReferenceLookupType.setCustomDisable( ! isEditing );
		}
		if( javafxEditorId != null ) {
			javafxEditorId.setDisable( true );
		}
		if( javafxEditorName != null ) {
			javafxEditorName.setDisable( ! isEditing );
		}
		if( javafxEditorShortDescription != null ) {
			javafxEditorShortDescription.setDisable( ! isEditing );
		}
		if( javafxEditorDescription != null ) {
			javafxEditorDescription.setDisable( ! isEditing );
		}
		if( javafxEditorIsNullable != null ) {
			javafxEditorIsNullable.setDisable( ! isEditing );
		}
	}
}
