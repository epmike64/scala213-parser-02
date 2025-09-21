package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.Optional;

public abstract class fFun extends AstOperandNod {
	private final Optional<fModifiers> mods;
	public fFun(Optional<fModifiers> mods) {
		this.mods = mods;
	}
	public Optional<fModifiers> getMods() {
		return mods;
	}
}
