package cn.com.hq.util.chart;

import java.util.Calendar;
import java.util.List;

import javafx.scene.control.Tooltip;

public class Chartoption {
	private Title title;
	private Legend legend;
	private Grid grid;
	private MxAxis MxAxis;
	private YAxis yAxis;
	private Polar polar;
	private RadiusAxis radiusAxis;
	private AngleAxis angleAxis;
	private Radar radar;
	private DataZoom dataZoom;
	private VisualMap visualMap;
	private Tooltip tooltip;
	private AxisPointer axisPointer;
	private Toolbox toolbox;
	private Brush brush;
	private Geo geo;
	private Parallel parallel;
	private ParallelAxis parallelAxis;
	private SingleAxis singleAxis;
	private Timeline timeline;
	private Graphic  graphic ;
	private Calendar calendar;
	private List<Series> series;
	private String[] color;
	private Color backgroundColor;
	private TextStyle textStyle;
	private boolean animation;
	private int animationThreshold;
	private int animationDuration;
	private String animationEasing;
	private int animationDelay;
	private int animationDurationUpdate;
	private String 	animationEasingUpdace	;
	private int animationDelayUpdace;
	private int progressive;
	private int progressiveThreshold;
	private String blendMode;
	private int hoverLayerThreshold;
	private boolean useUTC;
	public Title getTitle() {
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public Legend getLegend() {
		return legend;
	}
	public void setLegend(Legend legend) {
		this.legend = legend;
	}
	public Grid getGrid() {
		return grid;
	}
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	public MxAxis getMxAxis() {
		return MxAxis;
	}
	public void setMxAxis(MxAxis mxAxis) {
		MxAxis = mxAxis;
	}
	public YAxis getyAxis() {
		return yAxis;
	}
	public void setyAxis(YAxis yAxis) {
		this.yAxis = yAxis;
	}
	public Polar getPolar() {
		return polar;
	}
	public void setPolar(Polar polar) {
		this.polar = polar;
	}
	public RadiusAxis getRadiusAxis() {
		return radiusAxis;
	}
	public void setRadiusAxis(RadiusAxis radiusAxis) {
		this.radiusAxis = radiusAxis;
	}
	public AngleAxis getAngleAxis() {
		return angleAxis;
	}
	public void setAngleAxis(AngleAxis angleAxis) {
		this.angleAxis = angleAxis;
	}
	public Radar getRadar() {
		return radar;
	}
	public void setRadar(Radar radar) {
		this.radar = radar;
	}
	public DataZoom getDataZoom() {
		return dataZoom;
	}
	public void setDataZoom(DataZoom dataZoom) {
		this.dataZoom = dataZoom;
	}
	public VisualMap getVisualMap() {
		return visualMap;
	}
	public void setVisualMap(VisualMap visualMap) {
		this.visualMap = visualMap;
	}
	public Tooltip getTooltip() {
		return tooltip;
	}
	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}
	public AxisPointer getAxisPointer() {
		return axisPointer;
	}
	public void setAxisPointer(AxisPointer axisPointer) {
		this.axisPointer = axisPointer;
	}
	public Toolbox getToolbox() {
		return toolbox;
	}
	public void setToolbox(Toolbox toolbox) {
		this.toolbox = toolbox;
	}
	public Brush getBrush() {
		return brush;
	}
	public void setBrush(Brush brush) {
		this.brush = brush;
	}
	public Geo getGeo() {
		return geo;
	}
	public void setGeo(Geo geo) {
		this.geo = geo;
	}
	public Parallel getParallel() {
		return parallel;
	}
	public void setParallel(Parallel parallel) {
		this.parallel = parallel;
	}
	public ParallelAxis getParallelAxis() {
		return parallelAxis;
	}
	public void setParallelAxis(ParallelAxis parallelAxis) {
		this.parallelAxis = parallelAxis;
	}
	public SingleAxis getSingleAxis() {
		return singleAxis;
	}
	public void setSingleAxis(SingleAxis singleAxis) {
		this.singleAxis = singleAxis;
	}
	public Timeline getTimeline() {
		return timeline;
	}
	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}
	public Graphic getGraphic() {
		return graphic;
	}
	public void setGraphic(Graphic graphic) {
		this.graphic = graphic;
	}
	public Calendar getCalendar() {
		return calendar;
	}
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
	public List<Series> getSeries() {
		return series;
	}
	public void setSeries(List<Series> series) {
		this.series = series;
	}
	public String[] getColor() {
		return color;
	}
	public void setColor(String[] color) {
		this.color = color;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public TextStyle getTextStyle() {
		return textStyle;
	}
	public void setTextStyle(TextStyle textStyle) {
		this.textStyle = textStyle;
	}
	public boolean isAnimation() {
		return animation;
	}
	public void setAnimation(boolean animation) {
		this.animation = animation;
	}
	public int getAnimationThreshold() {
		return animationThreshold;
	}
	public void setAnimationThreshold(int animationThreshold) {
		this.animationThreshold = animationThreshold;
	}
	public int getAnimationDuration() {
		return animationDuration;
	}
	public void setAnimationDuration(int animationDuration) {
		this.animationDuration = animationDuration;
	}
	public String getAnimationEasing() {
		return animationEasing;
	}
	public void setAnimationEasing(String animationEasing) {
		this.animationEasing = animationEasing;
	}
	public int getAnimationDelay() {
		return animationDelay;
	}
	public void setAnimationDelay(int animationDelay) {
		this.animationDelay = animationDelay;
	}
	public int getAnimationDurationUpdate() {
		return animationDurationUpdate;
	}
	public void setAnimationDurationUpdate(int animationDurationUpdate) {
		this.animationDurationUpdate = animationDurationUpdate;
	}
	public String getAnimationEasingUpdace() {
		return animationEasingUpdace;
	}
	public void setAnimationEasingUpdace(String animationEasingUpdace) {
		this.animationEasingUpdace = animationEasingUpdace;
	}
	public int getAnimationDelayUpdace() {
		return animationDelayUpdace;
	}
	public void setAnimationDelayUpdace(int animationDelayUpdace) {
		this.animationDelayUpdace = animationDelayUpdace;
	}
	public int getProgressive() {
		return progressive;
	}
	public void setProgressive(int progressive) {
		this.progressive = progressive;
	}
	public int getProgressiveThreshold() {
		return progressiveThreshold;
	}
	public void setProgressiveThreshold(int progressiveThreshold) {
		this.progressiveThreshold = progressiveThreshold;
	}
	public String getBlendMode() {
		return blendMode;
	}
	public void setBlendMode(String blendMode) {
		this.blendMode = blendMode;
	}
	public int getHoverLayerThreshold() {
		return hoverLayerThreshold;
	}
	public void setHoverLayerThreshold(int hoverLayerThreshold) {
		this.hoverLayerThreshold = hoverLayerThreshold;
	}
	public boolean isUseUTC() {
		return useUTC;
	}
	public void setUseUTC(boolean useUTC) {
		this.useUTC = useUTC;
	}
	
	
}
