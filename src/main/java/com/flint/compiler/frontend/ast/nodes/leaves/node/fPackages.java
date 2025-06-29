package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.ArrayList;
import java.util.List;

public class fPackages extends AstOperandNod {
	final List<fIds> fIds;
	public fPackages(List<fIds> fIds) {
		this.fIds = new ArrayList<>(fIds);
	}
}
