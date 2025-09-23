package com.flint.compiler.frontend.ast.nodes.leaves.node;

import com.flint.compiler.frontend.ast.nodes.AstNod;
import com.flint.compiler.frontend.ast.nodes.AstNodVisitor;
import com.flint.compiler.frontend.ast.nodes.AstOperandNod;

import java.util.ArrayList;
import java.util.List;

public class fCompilationUnit extends AstOperandNod {

	private final List<fPackage> packages = new ArrayList<>();
	private final List<fImport> imports = new ArrayList<>();
	private final List<AstNod> stmts = new ArrayList<>();

	public void setPackages(List<fPackage> packages) {
		assert packages != null && packages.size() > 0;
		this.packages.addAll(packages);
	}

	public void addImport(fImport importNod) {
		assert importNod != null;
		imports.add(importNod);
	}

	public void addStmt(AstNod stmt) {
		assert stmt != null;
		stmts.add(stmt);
	}

	public List<fPackage> getPackages() {
		return packages;
	}

	public List<fImport> getImports() {
		return imports;
	}

	public List<AstNod> getStatements() {
		return stmts;
	}

	@Override
	public void accept(AstNodVisitor visitor) {
		visitor.visit(this);
	}
}
