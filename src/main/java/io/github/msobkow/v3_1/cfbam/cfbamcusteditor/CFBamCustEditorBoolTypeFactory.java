// Description: Java 25 JavaFX Display Element Factory for BoolType.

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
 *	CFBamJavaFXBoolTypeFactory JavaFX Display Element Factory
 *	for BoolType.
 */
public class CFBamCustEditorBoolTypeFactory
extends CFBamJavaFXBoolTypeFactory
{
	public CFBamCustEditorBoolTypeFactory( ICFBamJavaFXSchema argSchema ) {
		super( argSchema );
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFBamBoolTypeObj argFocus ) {
		CFBamCustEditorBoolTypeAttrPane retnew = new CFBamCustEditorBoolTypeAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFBamSchemaDefObj argContainer,
		ICFBamBoolTypeObj argFocus,
		Collection<ICFBamBoolTypeObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFBamJavaFXBoolTypeListPane retnew = new CFBamJavaFXBoolTypeListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFBamBoolTypeObj argFocus,
		ICFBamSchemaDefObj argContainer,
		Collection<ICFBamBoolTypeObj> dataCollection,
		ICFBamJavaFXBoolTypeChosen whenChosen )
	{
		CFBamJavaFXBoolTypePickerPane retnew = new CFBamJavaFXBoolTypePickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			dataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFBamBoolTypeObj argFocus ) {
		CFBamJavaFXBoolTypeEltTabPane retnew = new CFBamJavaFXBoolTypeEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFBamBoolTypeObj argFocus ) {
		CFBamJavaFXBoolTypeAddPane retnew = new CFBamJavaFXBoolTypeAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFBamBoolTypeObj argFocus ) {
		CFBamJavaFXBoolTypeViewEditPane retnew = new CFBamJavaFXBoolTypeViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFBamBoolTypeObj argFocus, ICFDeleteCallback callback ) {
		CFBamJavaFXBoolTypeAskDeleteForm retnew = new CFBamJavaFXBoolTypeAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFBamBoolTypeObj argFocus,
		ICFBamSchemaDefObj argContainer,
		Collection<ICFBamBoolTypeObj> argDataCollection,
		ICFBamJavaFXBoolTypeChosen whenChosen )
	{
		CFBamJavaFXBoolTypePickerForm retnew = new CFBamJavaFXBoolTypePickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFBamBoolTypeObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFBamJavaFXBoolTypeAddForm retnew = new CFBamJavaFXBoolTypeAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFBamBoolTypeObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFBamCustEditorBoolTypeViewEditForm retnew = new CFBamCustEditorBoolTypeViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
