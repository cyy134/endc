package com.example.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

public class WebSocketHandlerTwo extends SimpleChannelInboundHandler<Object> {

    //保存所有的连接信息
    private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for(Channel channel1: group){
            channel1.writeAndFlush(new TextWebSocketFrame(channel1.remoteAddress()+"加入聊天室"));
        }
        group.add(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.print(ctx.channel().remoteAddress()+"在线"+"\n");
        Channel channel = ctx.channel();
        group.add(channel);
        System.out.print("当前在线人数: "+group.size()+"\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        group.remove(channel);
        System.out.print(channel.remoteAddress()+"已经掉线"+"\n");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.print(ctx.channel().remoteAddress()+"已经离线"+"\n");
        Channel channel = ctx.channel();
        for(Channel channel1: group){
            channel1.writeAndFlush(new TextWebSocketFrame(channel.remoteAddress()+"已经离线"));
        }
        System.out.print("当前在线人数： "+group.size()+"\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        String request = ((TextWebSocketFrame) msg).text();
        for(Channel channel1: group){
            channel1.writeAndFlush(new TextWebSocketFrame(request));
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生:" + cause.getMessage());
        ctx.close();//关闭连接
    }

}
