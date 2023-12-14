(function(jq){
  jq.fn.jqTreeTable=function(map, options){
    var opts = jq.extend({openImg:"",shutImg:"",leafImg:"",lastOpenImg:"",lastShutImg:"",lastLeafImg:"",vertLineImg:"",blankImg:"",collapse:false,column:0,striped:false,highlight:false,state:true},options),
    mapa=[],mapb=[],tid=this.attr("id"),collarr=[],
	  stripe=function(){
      //if(opts.striped){
  		  //$("#"+tid+" tr:visible").filter(":even").addClass("even").end().filter(":odd").removeClass("even");
      //}
	  },
    buildText = function(parno, preStr){//Recursively build up the text for the images that make it work
      var mp=mapa[parno], ro=0, pre="", pref, img;
      for (var y=0,yl=mp.length;y<yl;y++){
        ro = mp[y];
        if (mapa[ro]){//It's a parent as well. Build it's string and move on to it's children
          pre=(y==yl-1)? opts.blankImg: opts.vertLineImg;
          img=(y==yl-1)? opts.lastOpenImg: opts.openImg;
          mapb[ro-1] = preStr + '<img src="'+img+'" class="parimg" id="'+tid+ro+'">';
          pref = preStr + '<img src="'+pre+'" class="preimg">';
          arguments.callee(ro, pref);
        }else{//it's a childz
          img = (y==yl-1)? opts.lastLeafImg: opts.leafImg;//It's the last child, It's child will have a blank field behind it
          mapb[ro-1] = preStr + '<img src="'+img+'" class="ttimage" id="'+tid+ro+'">';
        }
      }
    },
    expandKids = function(num, last){//Expands immediate children, and their uncollapsed children
      jq("#"+tid+num).attr("src", (last)? opts.lastOpenImg: opts.openImg);//
      for (var x=0, xl=mapa[num].length;x<xl;x++){
        var mnx = mapa[num][x];
        jq("#"+tid+mnx).parents("tr:not(.banner)").removeClass("collapsed");
  			if (mapa[mnx] && opts.state && jq.inArray(mnx, collarr)<0){////If it is a parent and its number is not in the collapsed array
          arguments.callee(mnx,(x==xl-1));//Expand it. More intuitive way of displaying the tree
        }
      }
    },
    collapseKids = function(num, last){//Recursively collapses all children and their children and change icon
      jq("#"+tid+num).attr("src", (last)? opts.lastShutImg: opts.shutImg);
      for (var x=0, xl=mapa[num].length;x<xl;x++){
        var mnx = mapa[num][x];
        jq("#"+tid+mnx).parents("tr:not(.banner)").addClass("collapsed");
        if (mapa[mnx]){//If it is a parent
          arguments.callee(mnx,(x==xl-1));
        }
      }
    },
  	creset = function(num, exp){//Resets the collapse array
  		var o = (exp)? collarr.splice(jq.inArray(num, collarr), 1): collarr.push(num);
      cset(tid,collarr);
  	},
  	cget = function(n){
	  	var v='',c=' '+document.cookie+';',s=c.indexOf(' '+n+'=');
	  	//JORGE'S MODIFICATION - INI
	  	if (document.cookie==(n+'=')) s=-1;
	  	//JORGE'S MODIFICATION - FIN
	    if (s>=0) {
	    	s+=n.length+2; //c=" treet1=;"
	      v=(c.substring(s,c.indexOf(';',s))).split("|");
	    }
	    return v||0;
  	},
    cset = function (n,v) {
  		jq.unique(v);
	  	document.cookie = n+"="+v.join("|")+";";
	  };
    for (var x=0,xl=map.length; x<xl;x++){//From map of parents, get map of kids
      num = map[x];
      if (!mapa[num]){
        mapa[num]=[];
      }
      mapa[num].push(x+1);
    }
    buildText(0,"");
    jq("tr:not(.banner)", this).each(function(i){//Inject the images into the column to make it work
      jq(this).children("td").eq(opts.column).prepend(mapb[i]);
      //jq(this).children("td").eq(4).prepend("["+((mapa[i+1])? mapa[i+1]: "Child")+"]");//REMOVE THIS for production
    });
		collarr = cget(tid)||opts.collapse||collarr;
		if (collarr.length){
			cset(tid,collarr);
	    for (var y=0,yl=collarr.length;y<yl;y++){
	      collapseKids(collarr[y],($("#"+collarr[y]+ " .parimg").attr("src")==opts.lastOpenImg));
	    }
		}
    stripe();
    jq(".parimg", this).each(function(i){
	  //var jqt = $(this).find("img.parimg"),last;
	  var jqt = jq(this),last;
	  var jcl = $(this).parent().parent(),last; //Obtenemos el TR correspondiente a la línea

      jcl.click(function(){
        var num = parseInt(jqt.attr("id").substr(tid.length));//Number of the row
        if (jqt.parents("tr:not(.banner)").next().is(".collapsed")){//If the table row directly below is collapsed
          expandKids(num, (jqt.attr("src")==opts.lastShutImg));//Then expand all children not in collarr
					if(opts.state){creset(num,true);}//If state is set, store in cookie
        }else{//Collapse all and set image to opts.shutImg or opts.lastShutImg on parents
          collapseKids(num, (jqt.attr("src")==opts.lastOpenImg));
					if(opts.state){creset(num,false);}//If state is set, store in cookie
        }
        stripe();//Restripe the rows
      });
    });
    if (opts.highlight){//This is where it highlights the rows
      jq("tr:not(.banner)", this).hover(
      	//JORGE'S MODIFICATION - INI
        function(){
        	var classToDelete=jq(this).attr("class");
         	jq(this).removeClass(classToDelete);
         	var classToAdd = "overMethod";
         	if (classToDelete=="testrun") classToAdd="overTestrun";
         	if (classToDelete=="method") classToAdd="overMethod";
         	if (classToDelete=="step") classToAdd="overStep";
         	if (classToDelete=="validation") classToAdd="overValidation";
        	jq(this).addClass(classToAdd);
        },
        function(){
        	var classToDelete=jq(this).attr("class");
         	jq(this).removeClass(classToDelete);
         	var classToAdd = "method";
         	if (classToDelete=="overTestrun") classToAdd="testrun";
         	if (classToDelete=="overMethod") classToAdd="method";
         	if (classToDelete=="overStep") classToAdd="step";
         	if (classToDelete=="overValidation") classToAdd="validation";
        	jq(this).addClass(classToAdd);
        }
        //JORGE'S MODIFICATION - FIN
      );
    };
  };
  return this;
})(jQuery);

//JORGE'S MODIFICATION - INI
$(document).ready(function(){
    $('.link-sort-table').click(function(e) {
        var $sort = this;
        var $table = $('#tableMain');
        var $rows = $('tbody > tr.method', $table);
        
        $rows.sort(function(a, b) {
            var keyA = $('td:eq(1)', a).text();
            var keyB = $('td:eq(1)', b).text();
            var keyAres = $(a).attr('met');
            var keyBres = $(b).attr('met');
            
            if ($($sort).hasClass('asc')) {
                return (keyA > keyB) ? 1 : -1;
            } else {
                if ($($sort).hasClass('desc')) {
                    return (keyA < keyB) ? 1 : -1;
                } else {
                    return (keyAres > keyBres) ? 1 : -1;
                }
            }
        })
		
		$.each($rows, function(index, row){
		  var $myrow = $(row);
		  var $rows2 = $("tbody > tr[met='" + $myrow.attr('met') +"']",$table);
		  $table.append($rows2);
		});		

        e.preventDefault();
    });
});

  var sBrowser; 
  var sVersion; 
 
  function setBrowserType(){ 
    var aBrowFull = new Array("opera", "msie", "netscape", "gecko", "mozilla"); 
    var aBrowVers = new Array("opera", "msie", "netscape", "rv",    "mozilla"   ); 
    var aBrowAbrv = new Array("op",    "ie",   "ns",       "mo",    "ns"   ); 
    var sInfo = navigator.userAgent.toLowerCase();; 
 
    sBrowser = ""; 
    for (var i = 0; i < aBrowFull.length; i++){ 
     if ((sBrowser == "") && (sInfo.indexOf(aBrowFull[i]) != -1)){ 
      sBrowser = aBrowAbrv[i]; 
      sVersion = String(parseFloat(sInfo.substr(sInfo.indexOf(aBrowVers[i]) + aBrowVers[i].length + 1))); 
     } 
    } 
  } 

  function getBrowserName(){ 
    return sBrowser; 
  } 
 
  function getBrowserVersion(){ 
    return sVersion; 
  } 
  
  var sTableOrigin;
  function setSizeTable() { 
    sTableOrigin = (document.getElementById('tableMain').scrollWidth);
  } 

  function show_hide_all(id_table, do_show, pathScripts) {

    var tbl  = document.getElementById(id_table);
    var rows = tbl.getElementsByTagName('tr');
    var imagesRow = null;
    var imgOpen = pathScripts + "/static/images/fopen.gif";
    var imgShut = pathScripts + "/static/images/fshut.gif";
    
    for (var row=0; row<rows.length; row++) {
    	if (rows[row].className=='method' || rows[row].className=='testrun') {
    		imagesRow = rows[row].getElementsByTagName('img');
    		for (var img=0; img<imagesRow.length; img++) 
    			if (imagesRow[img].className=='parimg') {
    				if (do_show) imagesRow[img].src=imgOpen;
    				else imagesRow[img].src=imgShut;
    			}
    		}	
    	
    	if (rows[row].className=='step collapsed')
    		if (do_show) {
    		  rows[row].className=rows[row].className.replace(' collapsed', '');
    		  imagesRow = rows[row].getElementsByTagName('img');
    		  for (var img=0; img<imagesRow.length; img++) 
    		  	if (imagesRow[img].className=='parimg') imagesRow[img].src=imgOpen;
    		}
    		
    	if (rows[row].className=='step')
    		if (!do_show) {
    			rows[row].className=rows[row].className+' collapsed';
    			imagesRow = rows[row].getElementsByTagName('img');
    		  for (var img=0; img<imagesRow.length; img++) 
    		  	if (imagesRow[img].className=='parimg') imagesRow[img].src=imgShut;
    		}
    		
    	if (rows[row].className=='validation collapsed')
    		if (do_show) 
    		  rows[row].className=rows[row].className.replace(' collapsed', '');
    	if (rows[row].className=='validation')
    		if (!do_show)
    			rows[row].className=rows[row].className+' collapsed';
    }
    
    if (do_show) 
    	document.getElementById('divShow').innerHTML = '<A id=linkShow href="javascript:show_hide_all(\'tableMain\',false, outputReports);">Hide All</A>';
  	else 
  		document.getElementById('divShow').innerHTML = '<A id=linkShow href="javascript:show_hide_all(\'tableMain\',true, outputReports);">Show All</A>';
	}  
//JORGE'S MODIFICATION - FIN 

