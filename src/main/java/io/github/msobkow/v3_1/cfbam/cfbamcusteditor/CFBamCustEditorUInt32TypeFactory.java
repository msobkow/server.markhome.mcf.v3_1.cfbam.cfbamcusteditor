// Description: Java 25 JavaFX Display Element Factory for UInt32Type.

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

/**
 *	CFBamJavaFXUInt32TypeFactory JavaFX Display Element Factory
 *	for UInt32Type.
 */
public class CFBamCustEditorUInt32TypeFactory
extends CFBamJavaFXUInt32TypeFactory
{
	public CFBamCustEditorUInt32TypeFactory( ICFBamJavaFXSchema argSchema ) {
		super( argSchema );
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFBamUInt32TypeObj argFocus ) {
		CFBamCustEditorUInt32TypeAttrPane retnew = new CFBamCustEditorUInt32TypeAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFBamSchemaDefObj argContainer,
		ICFBamUInt32TypeObj argFocus,
		Collection<ICFBamUInt32TypeObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFBamJavaFXUInt32TypeListPane retnew = new CFBamJavaFXUInt32TypeListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFBamUInt32TypeObj argFocus,
		ICFBamSchemaDefObj argContainer,
		Collection<ICFBamUInt32TypeObj> dataCollection,
		ICFBamJavaFXUInt32TypeChosen whenChosen )
	{
		CFBamJavaFXUInt32TypePickerPane retnew = new CFBamJavaFXUInt32TypePickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			dataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFBamUInt32TypeObj argFocus ) {
		CFBamJavaFXUInt32TypeEltTabPane retnew = new CFBamJavaFXUInt32TypeEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFBamUInt32TypeObj argFocus ) {
		CFBamJavaFXUInt32TypeAddPane retnew = new CFBamJavaFXUInt32TypeAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFBamUInt32TypeObj argFocus ) {
		CFBamJavaFXUInt32TypeViewEditPane retnew = new CFBamJavaFXUInt32TypeViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFBamUInt32TypeObj argFocus, ICFDeleteCallback callback ) {
		CFBamJavaFXUInt32TypeAskDeleteForm retnew = new CFBamJavaFXUInt32TypeAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFBamUInt32TypeObj argFocus,
		ICFBamSchemaDefObj argContainer,
		Collection<ICFBamUInt32TypeObj> argDataCollection,
		ICFBamJavaFXUInt32TypeChosen whenChosen )
	{
		CFBamJavaFXUInt32TypePickerForm retnew = new CFBamJavaFXUInt32TypePickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFBamUInt32TypeObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFBamJavaFXUInt32TypeAddForm retnew = new CFBamJavaFXUInt32TypeAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFBamUInt32TypeObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFBamCustEditorUInt32TypeViewEditForm retnew = new CFBamCustEditorUInt32TypeViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
