<#import "../taim_template/taims_layout.ftl" as layout>
<@layout.taims_layout  "${(title)!}" "${(username)!}" "${(userId)!}" "${(userName)!}" >
    <#assign ctx = rc.getContextPath()/>

<div class="row">
    <form action="${ctx}/incident/saveIncident.ibbl" method="POST" class="form-horizontal" role="form">

        <div class="col-xs-6">
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> Reference No </label>

                <div class="col-sm-9">
                    <input name="refNo" value="${refNo!}" type="text" id="form-field-1" placeholder=""
                           class="col-xs-10 col-sm-5"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="incident-type"> Incident Type</label>

                <div class="col-sm-9">
                        <select name="type" class="chosen-select form-control" id="incident-type" data-placeholder="Choose a State...">
                        <option value="">&nbsp;</option>
                        <#list TU.getIncidentTypeList() as type>
                            <option value="${type?index}">${type}</option>
                        </#list>
                    </select>
                </div>
            </div>


            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="user-name"> Initiator Name </label>

                <div class="col-sm-9">
                    <input type="text" readonly=""  value="${userName!}" id="user-name" placeholder="Text Field"
                           class="form-control"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="incident-date"> Incident Date </label>

                <div class="col-sm-9">
                    <div class="input-group"><span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i> </span>
                        <input name="incidentDate" value="10/02/2017" class="date-picker  col-sm-4" id="incident-date" type="text"
                               data-date-format="dd-mm-yyyy"/>

                    </div>

                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="id-date-picker-1"> Reporting Date</label>

                <div class="col-sm-9">
                    <div class="input-group"><span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i> </span>
                        <input name="reportingDate" value="${reportingDate?string('dd/MM/yyyy')}" class="date-picker  col-sm-4" id="id-date-picker-1" type="text"
                               data-date-format="dd-mm-yyyy"/>

                    </div>

                </div>
            </div>

            <div class="space-4"></div>


            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="loss-amt"> Appox. Loss Amt. </label>

                <div class="col-sm-9">
                    <input name="lossAmt" type="number" value="125400" id="loss-amt" placeholder="Text Field" class=" col-sm-5"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="incident-started-from">Duration </label>

                <div class="col-sm-4">
                    <input name="startedFrom" id="incident-started-from" type="text" class="form-control" />
                </div>
                <label class="col-sm-1 control-label text-center" for="incident-stopped-at">to</label>

                <div class="col-sm-4">
                        <input name="stoppedAt" id="incident-stopped-at" type="text" class="form-control" />
                </div>
            </div>

            <div class="space-4"></div>



            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                    <button class="btn btn-info" type="submit">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        Submit
                    </button>

                    &nbsp; &nbsp; &nbsp;
                    <button class="btn" type="reset">
                        <i class="ace-icon fa fa-undo bigger-110"></i>
                        Reset
                    </button>
                </div>
            </div>


        </div>
        <div class="col-xs-6">
            <div class="form-group">
                <label class="col-sm-12 " for="incident-details">Details of Incident(with Effect of to
                    Business)</label>
            </div>

            <div class="form-group">
                <div class="col-sm-12">
                    <textarea name="details" id="incident-details" class="autosize-transition form-control">This is incident Details</textarea>

                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-9">
                    <div class="inline">
                        <div class="checkbox">
                            <label>
                                <input name="noControllingApparatus" class="ace ace-checkbox-2  <#--input-lg-->" type="checkbox" onclick="$('.control-already-exists').toggle()"/>
                                <span class="lbl"> Preventative Control did not exists</span>
                            </label>
                        </div>

                    </div>
                </div>
            </div>

            <div class="form-group control-already-exists">
                <label class="col-sm-12 " for="failure-reasons">Control Details</label>
            </div>

            <div class="form-group control-already-exists">
                <div class="col-sm-12">
                    <textarea name="failureReasons" id="failure-reasons" class="autosize-transition form-control">This is a reason to fails</textarea>

                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-12 " for="preventative-controls">Preventative Controls Employed for Future Re-occurrence</label>
            </div>

            <div class="form-group">
                <div class="col-sm-12">
                    <textarea name="preventativeControls" id="preventative-controls" class="autosize-transition form-control">This controls has been employed. Do u like it !</textarea>

                </div>
            </div>
        </div>
    </form>
</div>

</@layout.taims_layout>

<script>

</script>