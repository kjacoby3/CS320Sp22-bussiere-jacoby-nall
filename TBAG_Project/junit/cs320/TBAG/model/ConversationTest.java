package cs320.TBAG.model;
import org.junit.Before;
import org.junit.Test;

import cs320.TBAG.model.Convo.Conversation;
import cs320.TBAG.model.Convo.ConversationNode;
import cs320.TBAG.model.Convo.ConversationResponse;
import cs320.TBAG.model.Convo.ConversationTree;
import cs320.TBAG.model.Convo.DefaultResponse;
import cs320.TBAG.model.Convo.EndResponse;

import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.Assert.*;
import junit.framework.TestCase;

public class ConversationTest extends TestCase {
	private Player player;
	private NPC npc;
	
	@Before
	protected void setUp() throws Exception{
		player = new Player();
		npc = new NPC();
	}
	
	@Test
	public void testSelectBlankConversationResponse1() {
		Conversation conversation = new Conversation(player, npc);
		conversation.selectResponse(1);
		assertTrue(conversation.getSelectedResponse() == npc.getConversationTree().getNode(1).getResponse(1));
	}
	
	@Test
	public void testSelectBlankConversationResponse2() {
		Conversation conversation = new Conversation(player, npc);
		conversation.selectResponse(2);
		assertTrue(conversation.selectResponse(2) == npc.getConversationTree().getNode(1).getResponse(2));
	}
	
	@Test
	public void testAddBlankNodeToBlankConversation() {
		Conversation conversation = new Conversation(player, npc);
		ConversationNode node2 = new ConversationNode();
		npc.getConversationTree().addNode(2, node2);
		conversation.selectNode(2);
		assertEquals(node2, conversation.getSelectedNode());
	}
	
	@Test
	public void testAddBlankDefaultResponseToBlankNode() {
		Conversation conversation = new Conversation(player, npc);
		DefaultResponse response = new DefaultResponse();
		npc.getConversationTree().getNode(1).addResponse(response);
		assertEquals(conversation.selectResponse(3), response);
		assertTrue(response == npc.getConversationTree().getNode(1).getResponse(3));
	}
	
	@Test
	public void testRemoveAddThenSelectDefaultResponseToBlankNode() {
		Conversation conversation = new Conversation(player, npc);
		DefaultResponse response = new DefaultResponse();
		npc.getConversationTree().getNode(1).removeResponse(2);
		npc.getConversationTree().getNode(1).addResponse(response);
		assertEquals(conversation.selectResponse(2), response);
		assertTrue(response == npc.getConversationTree().getNode(1).getResponse(2));
	}
	
	@Test
	public void testAddRemoveThenSelectDefaultResponseToBlankNode() {
		Conversation conversation = new Conversation(player, npc);
		DefaultResponse response = new DefaultResponse();
		npc.getConversationTree().getNode(1).addResponse(response);
		npc.getConversationTree().getNode(1).removeResponse(2);
		assertEquals(conversation.selectResponse(2), response);
		assertTrue(response == npc.getConversationTree().getNode(1).getResponse(2));
	}
	
	@Test
	public void testAddResponseToNavigateToNewNode() {
		Conversation conversation = new Conversation(player, npc);
		DefaultResponse response = new DefaultResponse("Go to Node 2", 2);
		ConversationNode node2 = new ConversationNode();
		node2.setStatement("Node 2");
		
		npc.getConversationTree().getNode(1).addResponse(response);
		npc.getConversationTree().addNode(2, node2);
		
		assertEquals(conversation.selectResponse(3), response);
		assertTrue(response == npc.getConversationTree().getNode(1).getResponse(3));
		
		assertEquals(conversation.getSelectedNode(), node2);
		assertTrue(node2 == npc.getConversationTree().getNode(2));
		
	}
	
	@Test 
	public void testAddAndSelectEndResponseToBlankNode() {
		Conversation conversation = new Conversation(player, npc);
		EndResponse endResponse = new EndResponse();
		
		npc.getConversationTree().getNode(1).addResponse(endResponse);
		
		assertEquals(conversation.selectResponse(3), endResponse);
		assertTrue(endResponse == npc.getConversationTree().getNode(1).getResponse(3));
		assertEquals(conversation.getSelectedNode(), npc.getConversationTree().getEndNode());
	}
	
	@Test
	public void testBuildAndNavigateConversationTreeNoLoop() {
		String stmt0 = "Good bye.";
		ArrayList<ConversationResponse> endList = new ArrayList<ConversationResponse>();
		ConversationNode node0 = new ConversationNode(stmt0, endList);
		
		String stmt1 = "Hello traveler!";
		ArrayList<ConversationResponse> respList1 = new ArrayList<ConversationResponse>();
		DefaultResponse resp11 = new DefaultResponse("Hello sir!", 2);
		DefaultResponse resp12 = new DefaultResponse("Do you know where the bathroom is?", 3);
		DefaultResponse resp13 = new DefaultResponse("Donde esta el bano?", 4);
		respList1.add(resp11);
		respList1.add(resp12);
		respList1.add(resp13);
		ConversationNode node1 = new ConversationNode(stmt1, respList1);
		
		String stmt2 = "Do you require assistance?";
		ArrayList<ConversationResponse> respList2 = new ArrayList<ConversationResponse>();
		DefaultResponse resp21 = new DefaultResponse("Yes please, do you know where I can find the bathroom?", 3);
		EndResponse resp22 = new EndResponse("No, thanks.");
		respList2.add(resp21);
		respList2.add(resp22);
		ConversationNode node2 = new ConversationNode(stmt2, respList2);
		
		String stmt3 = "Yes, it is down the hall, third dorr on your right.";
		ArrayList<ConversationResponse> respList3 = new ArrayList<ConversationResponse>();
		EndResponse resp31 = new EndResponse("Thanks");
		respList3.add(resp31);
		ConversationNode node3 = new ConversationNode(stmt3, respList3);
		
		String stmt4 = "Sorry, I don't speak spanish :(";
		ArrayList<ConversationResponse> respList4 = new ArrayList<ConversationResponse>();
		EndResponse resp41 = new EndResponse("No problem, bye");
		respList4.add(resp41);
		ConversationNode node4 = new ConversationNode(stmt4, respList4);
		
		TreeMap<Integer, ConversationNode> conversationTreeMap = new TreeMap<Integer, ConversationNode>();
		
		conversationTreeMap.put(0, node0);
		conversationTreeMap.put(1, node1);
		conversationTreeMap.put(2, node2);
		conversationTreeMap.put(3, node3);
		conversationTreeMap.put(4, node4);
		
		ConversationTree conversationTree = new ConversationTree(conversationTreeMap);
		
		npc.setConversationTree(conversationTree);
		
		// Conversation Navigation 1
		Conversation conversation = new Conversation(player, npc);
		
		assertEquals(npc.getConversationTree().getNode(1), conversation.getSelectedNode());
		
		conversation.selectResponse(1);
		assertEquals(npc.getConversationTree().getNode(1).getResponse(1), conversation.getSelectedResponse());
		assertEquals(npc.getConversationTree().getNode(2), conversation.getSelectedNode());
		
		conversation.selectResponse(1);
		assertEquals(npc.getConversationTree().getNode(2).getResponse(1), conversation.getSelectedResponse());
		assertEquals(npc.getConversationTree().getNode(3), conversation.getSelectedNode());
		
		conversation.selectResponse(1);
		assertEquals(npc.getConversationTree().getNode(3).getResponse(1), conversation.getSelectedResponse());
		assertEquals(npc.getConversationTree().getEndNode(), conversation.getSelectedNode());
		
		// Conversation Navigation 2
		Conversation conversation2 = new Conversation(player, npc);
		
		assertEquals(npc.getConversationTree().getNode(1), conversation2.getSelectedNode());
		
		conversation2.selectResponse(2);
		assertEquals(npc.getConversationTree().getNode(1).getResponse(2), conversation2.getSelectedResponse());
		assertEquals(npc.getConversationTree().getNode(3), conversation2.getSelectedNode());
		
		conversation2.selectResponse(1);
		assertEquals(npc.getConversationTree().getNode(3).getResponse(1), conversation2.getSelectedResponse());
		assertEquals(npc.getConversationTree().getEndNode(), conversation2.getSelectedNode());
		
		// Conversation Navigation 3
		Conversation conversation3 = new Conversation(player, npc);
		
		assertEquals(npc.getConversationTree().getNode(1), conversation3.getSelectedNode());
		
		conversation3.selectResponse(3);
		assertEquals(npc.getConversationTree().getNode(1).getResponse(3), conversation3.getSelectedResponse());
		assertEquals(npc.getConversationTree().getNode(4), conversation3.getSelectedNode());
		
		conversation3.selectResponse(1);
		assertEquals(npc.getConversationTree().getNode(4).getResponse(1), conversation3.getSelectedResponse());
		assertEquals(npc.getConversationTree().getEndNode(), conversation3.getSelectedNode());
		
		// Conversation Navigation 4
		Conversation conversation4 = new Conversation(player, npc);
		
		assertEquals(npc.getConversationTree().getNode(1), conversation4.getSelectedNode());
		
		conversation4.selectResponse(1);
		assertEquals(npc.getConversationTree().getNode(1).getResponse(1), conversation4.getSelectedResponse());
		assertEquals(npc.getConversationTree().getNode(2), conversation4.getSelectedNode());
		
		conversation4.selectResponse(2);
		assertEquals(npc.getConversationTree().getNode(2).getResponse(2), conversation4.getSelectedResponse());
		assertEquals(npc.getConversationTree().getEndNode(), conversation4.getSelectedNode());
	}
	
}