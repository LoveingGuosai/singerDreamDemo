riot.tag('pager', '<li class="{disable:no_previous()}" if="{page_links.length>1}"><a href="javascript:void(0)" onclick="{go_previous}">&laquo;</a></li> <li class="{active:parent.is_active(i)}" each="{ i in page_links}" if="{parent.page_links.length>1}" onclick="{parent.goto_item}"> <a href="javascript:void(0)">{i+1}<span class="sr-only" if="{is_active(i)}">(current)</span></a></li> <li class="{disable:no_next()}" if="{page_links.length>1}"><a href="javascript:void(0)" onclick="{go_next}">&raquo;</a></li>', function(opts) {
        var self = riot.observable(this);

        this.goto = function(page){
            $.get(self.opts.endpoint, _.extend(self.opts.parameters,{page:page}),function(data){
                self.data = data.page;

                self.trigger('page_data',data);
                self.update();
            });
        };

    
});