package programma;

public class Metadata {
	
		
		//attributi che descrivono i metadati
		private String alias;
		private String source;
		private String type;
		
		
	
		//costruttore
		public Metadata(String alias,String source,String type) {
			this.alias=alias;
			this.source=source;
			this.type=type;
		}


		
		
		//metodi get e set
		public String getAlias() {
			return alias;
		}


		public void setAlias(String alias) {
			this.alias = alias;
		}


		public String getSource() {
			return source;
		}


		public void setSource(String source) {
			this.source = source;
		}


		public String getType() {
			return type;
		}


		public void setType(String type) {
			this.type = type;
		}
		
}