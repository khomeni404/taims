<#import "../taim_template/taims_layout.ftl" as layout>
<@layout.taims_layout  "${(title)!}" "${(username)!}" "${(userId)!}" "${(userName)!}" >
    <#assign ctx = rc.getContextPath()/>
    <#assign inc = incident/>

<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <h4 class="header green clearfix">
            Review Note for ${inc.refNo!}
        </h4>

        <form action="${ctx}/incident/saveIncidentReview.ibbl" method="POST" class="form-horizontal" role="form">
            <input type="hidden" name="incidentId" value="${inc.id!}"/>
            <input type="hidden" name="reviewerId" value="${userId!}"/>

            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> Reference No </label>

                <div class="col-sm-9">
                    <input readonly name="refNo" value="${inc.refNo!}" type="text" id="form-field-1" placeholder=""
                           class="col-xs-10 col-sm-5"/>
                </div>
            </div>

            <div class="space-4"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> Reviewer </label>

                <div class="col-sm-9">
                    <input readonly name="refNo" value="${userName!}" type="text" id="form-field-1" placeholder=""
                           class="col-xs-10 col-sm-5"/>
                </div>
            </div>

            <div class="space-4"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="signedAs"> Reviewed as</label>

                <div class="col-sm-9">
                    <select name="signedAs" class="chosen-select form-control" id="signedAs"
                            data-placeholder="Choose a State...">
                        <option value="Head of ISRMD">Head of ISRMD</option>
                        <option value="Head of ICTW">Head of ICTW</option>
                        <option value="Head of SWD">Head of SWD</option>
                        <option value="Head of IOMD">Head of IOMD</option>
                        <option value="Head of ADD">Head of ADD</option>
                        <option value="Head of ...">Head of ...</option>
                    </select>
                </div>
            </div>

            <div class="space-4"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="reviewNote">Review Note</label>

                <div class="col-sm-9">
                    <textarea name="reviewNote" id="reviewNote" class="autosize-transition form-control">This is incident a sample incident review Note</textarea>

                </div>
            </div>


            <div class="space-4"></div>


            <div class="col-lg-12 text-right">
                <button class="btn btn-white btn-info btn-bold"
                        onclick="window.location = '${ctx}/incident/createIncidentReview.ibbl?incidentId=${inc.id}'">
                    <i class="ace-icon fa fa-floppy-o bigger-120 blue"></i>
                    Save Review
                </button>

            </div>


        </form>
    </div>
</div>
<div class="hr hr-dotted hr-16"></div>


</@layout.taims_layout>