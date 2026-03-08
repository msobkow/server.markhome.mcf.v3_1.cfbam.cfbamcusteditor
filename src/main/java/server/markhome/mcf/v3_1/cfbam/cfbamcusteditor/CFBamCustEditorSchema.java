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

/**
 *	The CFBamCustSchema defines the interface object that is
 *	provided by the custom interface for manipulating the CFBam
 *	facet in the user interface.
 */
public class CFBamCustEditorSchema
extends CFBamCustSchema
implements ICFBamCustEditorSchema
{
	public CFBamCustEditorSchema() {
		super();
	}

	@Override public ICFBamJavaFXBlobColFactory getBlobColFactory() {
		if( factoryBlobCol == null ) {
			factoryBlobCol = new CFBamCustEditorBlobColFactory( this );
		}
		return( factoryBlobCol );
	}

	@Override public ICFBamJavaFXBlobTypeFactory getBlobTypeFactory() {
		if( factoryBlobType == null ) {
			factoryBlobType = new CFBamCustEditorBlobTypeFactory( this );
		}
		return( factoryBlobType );
	}

	@Override public ICFBamJavaFXBoolColFactory getBoolColFactory() {
		if( factoryBoolCol == null ) {
			factoryBoolCol = new CFBamCustEditorBoolColFactory( this );
		}
		return( factoryBoolCol );
	}

	@Override public ICFBamJavaFXBoolTypeFactory getBoolTypeFactory() {
		if( factoryBoolType == null ) {
			factoryBoolType = new CFBamCustEditorBoolTypeFactory( this );
		}
		return( factoryBoolType );
	}

	@Override public ICFBamJavaFXChainFactory getChainFactory() {
		if( factoryChain == null ) {
			factoryChain = new CFBamCustEditorChainFactory( this );
		}
		return( factoryChain );
	}

	@Override public ICFBamJavaFXClearTopDepFactory getClearTopDepFactory() {
		if( factoryClearTopDep == null ) {
			factoryClearTopDep = new CFBamCustEditorClearTopDepFactory( this );
		}
		return( factoryClearTopDep );
	}

	@Override public ICFBamJavaFXDateColFactory getDateColFactory() {
		if( factoryDateCol == null ) {
			factoryDateCol = new CFBamCustEditorDateColFactory( this );
		}
		return( factoryDateCol );
	}

	@Override public ICFBamJavaFXDateTypeFactory getDateTypeFactory() {
		if( factoryDateType == null ) {
			factoryDateType = new CFBamCustEditorDateTypeFactory( this );
		}
		return( factoryDateType );
	}

	@Override public ICFBamJavaFXDelTopDepFactory getDelTopDepFactory() {
		if( factoryDelTopDep == null ) {
			factoryDelTopDep = new CFBamCustEditorDelTopDepFactory( this );
		}
		return( factoryDelTopDep );
	}

	@Override public ICFBamJavaFXDoubleColFactory getDoubleColFactory() {
		if( factoryDoubleCol == null ) {
			factoryDoubleCol = new CFBamCustEditorDoubleColFactory( this );
		}
		return( factoryDoubleCol );
	}

	@Override public ICFBamJavaFXDoubleTypeFactory getDoubleTypeFactory() {
		if( factoryDoubleType == null ) {
			factoryDoubleType = new CFBamCustEditorDoubleTypeFactory( this );
		}
		return( factoryDoubleType );
	}

	@Override public ICFBamJavaFXEnumTagFactory getEnumTagFactory() {
		if( factoryEnumTag == null ) {
			factoryEnumTag = new CFBamCustEditorEnumTagFactory( this );
		}
		return( factoryEnumTag );
	}

	@Override public ICFBamJavaFXEnumTypeFactory getEnumTypeFactory() {
		if( factoryEnumType == null ) {
			factoryEnumType = new CFBamCustEditorEnumTypeFactory( this );
		}
		return( factoryEnumType );
	}

	@Override public ICFBamJavaFXFloatColFactory getFloatColFactory() {
		if( factoryFloatCol == null ) {
			factoryFloatCol = new CFBamCustEditorFloatColFactory( this );
		}
		return( factoryFloatCol );
	}

	@Override public ICFBamJavaFXFloatTypeFactory getFloatTypeFactory() {
		if( factoryFloatType == null ) {
			factoryFloatType = new CFBamCustEditorFloatTypeFactory( this );
		}
		return( factoryFloatType );
	}

	@Override public ICFBamJavaFXId16GenFactory getId16GenFactory() {
		if( factoryId16Gen == null ) {
			factoryId16Gen = new CFBamCustEditorId16GenFactory( this );
		}
		return( factoryId16Gen );
	}

	@Override public ICFBamJavaFXId32GenFactory getId32GenFactory() {
		if( factoryId32Gen == null ) {
			factoryId32Gen = new CFBamCustEditorId32GenFactory( this );
		}
		return( factoryId32Gen );
	}

	@Override public ICFBamJavaFXId64GenFactory getId64GenFactory() {
		if( factoryId64Gen == null ) {
			factoryId64Gen = new CFBamCustEditorId64GenFactory( this );
		}
		return( factoryId64Gen );
	}

	@Override public ICFBamJavaFXIndexColFactory getIndexColFactory() {
		if( factoryIndexCol == null ) {
			factoryIndexCol = new CFBamCustEditorIndexColFactory( this );
		}
		return( factoryIndexCol );
	}

	@Override public ICFBamJavaFXIndexFactory getIndexFactory() {
		if( factoryIndex == null ) {
			factoryIndex = new CFBamCustEditorIndexFactory( this );
		}
		return( factoryIndex );
	}

	@Override public ICFBamJavaFXInt16ColFactory getInt16ColFactory() {
		if( factoryInt16Col == null ) {
			factoryInt16Col = new CFBamCustEditorInt16ColFactory( this );
		}
		return( factoryInt16Col );
	}

	@Override public ICFBamJavaFXInt16TypeFactory getInt16TypeFactory() {
		if( factoryInt16Type == null ) {
			factoryInt16Type = new CFBamCustEditorInt16TypeFactory( this );
		}
		return( factoryInt16Type );
	}

	@Override public ICFBamJavaFXInt32ColFactory getInt32ColFactory() {
		if( factoryInt32Col == null ) {
			factoryInt32Col = new CFBamCustEditorInt32ColFactory( this );
		}
		return( factoryInt32Col );
	}

	@Override public ICFBamJavaFXInt32TypeFactory getInt32TypeFactory() {
		if( factoryInt32Type == null ) {
			factoryInt32Type = new CFBamCustEditorInt32TypeFactory( this );
		}
		return( factoryInt32Type );
	}

	@Override public ICFBamJavaFXInt64ColFactory getInt64ColFactory() {
		if( factoryInt64Col == null ) {
			factoryInt64Col = new CFBamCustEditorInt64ColFactory( this );
		}
		return( factoryInt64Col );
	}

	@Override public ICFBamJavaFXInt64TypeFactory getInt64TypeFactory() {
		if( factoryInt64Type == null ) {
			factoryInt64Type = new CFBamCustEditorInt64TypeFactory( this );
		}
		return( factoryInt64Type );
	}

	@Override public ICFBamJavaFXNmTokenColFactory getNmTokenColFactory() {
		if( factoryNmTokenCol == null ) {
			factoryNmTokenCol = new CFBamCustEditorNmTokenColFactory( this );
		}
		return( factoryNmTokenCol );
	}

	@Override public ICFBamJavaFXNmTokensColFactory getNmTokensColFactory() {
		if( factoryNmTokensCol == null ) {
			factoryNmTokensCol = new CFBamCustEditorNmTokensColFactory( this );
		}
		return( factoryNmTokensCol );
	}

	@Override public ICFBamJavaFXNmTokensTypeFactory getNmTokensTypeFactory() {
		if( factoryNmTokensType == null ) {
			factoryNmTokensType = new CFBamCustEditorNmTokensTypeFactory( this );
		}
		return( factoryNmTokensType );
	}

	@Override public ICFBamJavaFXNmTokenTypeFactory getNmTokenTypeFactory() {
		if( factoryNmTokenType == null ) {
			factoryNmTokenType = new CFBamCustEditorNmTokenTypeFactory( this );
		}
		return( factoryNmTokenType );
	}

	@Override public ICFBamJavaFXNumberColFactory getNumberColFactory() {
		if( factoryNumberCol == null ) {
			factoryNumberCol = new CFBamCustEditorNumberColFactory( this );
		}
		return( factoryNumberCol );
	}

	@Override public ICFBamJavaFXNumberTypeFactory getNumberTypeFactory() {
		if( factoryNumberType == null ) {
			factoryNumberType = new CFBamCustEditorNumberTypeFactory( this );
		}
		return( factoryNumberType );
	}

	@Override public ICFBamJavaFXParamFactory getParamFactory() {
		if( factoryParam == null ) {
			factoryParam = new CFBamCustEditorParamFactory( this );
		}
		return( factoryParam );
	}

	@Override public ICFBamJavaFXRelationColFactory getRelationColFactory() {
		if( factoryRelationCol == null ) {
			factoryRelationCol = new CFBamCustEditorRelationColFactory( this );
		}
		return( factoryRelationCol );
	}

	@Override public ICFBamJavaFXRelationFactory getRelationFactory() {
		if( factoryRelation == null ) {
			factoryRelation = new CFBamCustEditorRelationFactory( this );
		}
		return( factoryRelation );
	}

	@Override public ICFBamJavaFXSchemaDefFactory getSchemaDefFactory() {
		if( factorySchemaDef == null ) {
			factorySchemaDef = new CFBamCustEditorSchemaDefFactory( this );
		}
		return( factorySchemaDef );
	}

	@Override public ICFBamJavaFXSchemaRefFactory getSchemaRefFactory() {
		if( factorySchemaRef == null ) {
			factorySchemaRef = new CFBamCustEditorSchemaRefFactory( this );
		}
		return( factorySchemaRef );
	}

	@Override public ICFBamJavaFXServerListFuncFactory getServerListFuncFactory() {
		if( factoryServerListFunc == null ) {
			factoryServerListFunc = new CFBamCustEditorServerListFuncFactory( this );
		}
		return( factoryServerListFunc );
	}

	@Override public ICFBamJavaFXServerMethodFactory getServerMethodFactory() {
		if( factoryServerMethod == null ) {
			factoryServerMethod = new CFBamCustEditorServerMethodFactory( this );
		}
		return( factoryServerMethod );
	}

	@Override public ICFBamJavaFXServerObjFuncFactory getServerObjFuncFactory() {
		if( factoryServerObjFunc == null ) {
			factoryServerObjFunc = new CFBamCustEditorServerObjFuncFactory( this );
		}
		return( factoryServerObjFunc );
	}

	@Override public ICFBamJavaFXServerProcFactory getServerProcFactory() {
		if( factoryServerProc == null ) {
			factoryServerProc = new CFBamCustEditorServerProcFactory( this );
		}
		return( factoryServerProc );
	}

	@Override public ICFBamJavaFXStringColFactory getStringColFactory() {
		if( factoryStringCol == null ) {
			factoryStringCol = new CFBamCustEditorStringColFactory( this );
		}
		return( factoryStringCol );
	}

	@Override public ICFBamJavaFXStringTypeFactory getStringTypeFactory() {
		if( factoryStringType == null ) {
			factoryStringType = new CFBamCustEditorStringTypeFactory( this );
		}
		return( factoryStringType );
	}

	@Override public ICFBamJavaFXTableColFactory getTableColFactory() {
		if( factoryTableCol == null ) {
			factoryTableCol = new CFBamCustEditorTableColFactory( this );
		}
		return( factoryTableCol );
	}

	@Override public ICFBamJavaFXTableFactory getTableFactory() {
		if( factoryTable == null ) {
			factoryTable = new CFBamCustEditorTableFactory( this );
		}
		return( factoryTable );
	}

	@Override public ICFBamJavaFXTextColFactory getTextColFactory() {
		if( factoryTextCol == null ) {
			factoryTextCol = new CFBamCustEditorTextColFactory( this );
		}
		return( factoryTextCol );
	}

	@Override public ICFBamJavaFXTextTypeFactory getTextTypeFactory() {
		if( factoryTextType == null ) {
			factoryTextType = new CFBamCustEditorTextTypeFactory( this );
		}
		return( factoryTextType );
	}

	@Override public ICFBamJavaFXTimeColFactory getTimeColFactory() {
		if( factoryTimeCol == null ) {
			factoryTimeCol = new CFBamCustEditorTimeColFactory( this );
		}
		return( factoryTimeCol );
	}

	@Override public ICFBamJavaFXTimestampColFactory getTimestampColFactory() {
		if( factoryTimestampCol == null ) {
			factoryTimestampCol = new CFBamCustEditorTimestampColFactory( this );
		}
		return( factoryTimestampCol );
	}

	@Override public ICFBamJavaFXTimestampTypeFactory getTimestampTypeFactory() {
		if( factoryTimestampType == null ) {
			factoryTimestampType = new CFBamCustEditorTimestampTypeFactory( this );
		}
		return( factoryTimestampType );
	}

	@Override public ICFBamJavaFXTimeTypeFactory getTimeTypeFactory() {
		if( factoryTimeType == null ) {
			factoryTimeType = new CFBamCustEditorTimeTypeFactory( this );
		}
		return( factoryTimeType );
	}

	@Override public ICFBamJavaFXTokenColFactory getTokenColFactory() {
		if( factoryTokenCol == null ) {
			factoryTokenCol = new CFBamCustEditorTokenColFactory( this );
		}
		return( factoryTokenCol );
	}

	@Override public ICFBamJavaFXTokenTypeFactory getTokenTypeFactory() {
		if( factoryTokenType == null ) {
			factoryTokenType = new CFBamCustEditorTokenTypeFactory( this );
		}
		return( factoryTokenType );
	}

	@Override public ICFBamJavaFXTZDateColFactory getTZDateColFactory() {
		if( factoryTZDateCol == null ) {
			factoryTZDateCol = new CFBamCustEditorTZDateColFactory( this );
		}
		return( factoryTZDateCol );
	}

	@Override public ICFBamJavaFXTZDateTypeFactory getTZDateTypeFactory() {
		if( factoryTZDateType == null ) {
			factoryTZDateType = new CFBamCustEditorTZDateTypeFactory( this );
		}
		return( factoryTZDateType );
	}

	@Override public ICFBamJavaFXTZTimeColFactory getTZTimeColFactory() {
		if( factoryTZTimeCol == null ) {
			factoryTZTimeCol = new CFBamCustEditorTZTimeColFactory( this );
		}
		return( factoryTZTimeCol );
	}

	@Override public ICFBamJavaFXTZTimestampColFactory getTZTimestampColFactory() {
		if( factoryTZTimestampCol == null ) {
			factoryTZTimestampCol = new CFBamCustEditorTZTimestampColFactory( this );
		}
		return( factoryTZTimestampCol );
	}

	@Override public ICFBamJavaFXTZTimestampTypeFactory getTZTimestampTypeFactory() {
		if( factoryTZTimestampType == null ) {
			factoryTZTimestampType = new CFBamCustEditorTZTimestampTypeFactory( this );
		}
		return( factoryTZTimestampType );
	}

	@Override public ICFBamJavaFXTZTimeTypeFactory getTZTimeTypeFactory() {
		if( factoryTZTimeType == null ) {
			factoryTZTimeType = new CFBamCustEditorTZTimeTypeFactory( this );
		}
		return( factoryTZTimeType );
	}

	@Override public ICFBamJavaFXUInt16ColFactory getUInt16ColFactory() {
		if( factoryUInt16Col == null ) {
			factoryUInt16Col = new CFBamCustEditorUInt16ColFactory( this );
		}
		return( factoryUInt16Col );
	}

	@Override public ICFBamJavaFXUInt16TypeFactory getUInt16TypeFactory() {
		if( factoryUInt16Type == null ) {
			factoryUInt16Type = new CFBamCustEditorUInt16TypeFactory( this );
		}
		return( factoryUInt16Type );
	}

	@Override public ICFBamJavaFXUInt32ColFactory getUInt32ColFactory() {
		if( factoryUInt32Col == null ) {
			factoryUInt32Col = new CFBamCustEditorUInt32ColFactory( this );
		}
		return( factoryUInt32Col );
	}

	@Override public ICFBamJavaFXUInt32TypeFactory getUInt32TypeFactory() {
		if( factoryUInt32Type == null ) {
			factoryUInt32Type = new CFBamCustEditorUInt32TypeFactory( this );
		}
		return( factoryUInt32Type );
	}

	@Override public ICFBamJavaFXUInt64ColFactory getUInt64ColFactory() {
		if( factoryUInt64Col == null ) {
			factoryUInt64Col = new CFBamCustEditorUInt64ColFactory( this );
		}
		return( factoryUInt64Col );
	}

	@Override public ICFBamJavaFXUInt64TypeFactory getUInt64TypeFactory() {
		if( factoryUInt64Type == null ) {
			factoryUInt64Type = new CFBamCustEditorUInt64TypeFactory( this );
		}
		return( factoryUInt64Type );
	}

	@Override public ICFBamJavaFXUuidColFactory getUuidColFactory() {
		if( factoryUuidCol == null ) {
			factoryUuidCol = new CFBamCustEditorUuidColFactory( this );
		}
		return( factoryUuidCol );
	}

	@Override public ICFBamJavaFXUuidGenFactory getUuidGenFactory() {
		if( factoryUuidGen == null ) {
			factoryUuidGen = new CFBamCustEditorUuidGenFactory( this );
		}
		return( factoryUuidGen );
	}

	@Override public ICFBamJavaFXUuidTypeFactory getUuidTypeFactory() {
		if( factoryUuidType == null ) {
			factoryUuidType = new CFBamCustEditorUuidTypeFactory( this );
		}
		return( factoryUuidType );
	}

	@Override public ICFBamJavaFXValueFactory getValueFactory() {
		if( factoryValue == null ) {
			factoryValue = new CFBamCustEditorValueFactory( this );
		}
		return( factoryValue );
	}
}
