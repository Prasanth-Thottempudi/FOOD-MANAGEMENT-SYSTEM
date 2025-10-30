import { Component } from '@angular/core';

interface Agent {
  id: string;
  name: string;
  role: string;
  status: string;
  lastLogin: string;
}

@Component({
  selector: 'app-agentdashboard',
  templateUrl: './agentdashboard.component.html',
  styleUrls: ['./agentdashboard.component.css'],
})
export class AgentDashboardComponent {
  agentName = 'Prasanth Adolf';
  agents: Agent[] = [
    {
      id: 'AG101',
      name: 'Karthik',
      role: 'Field Sales',
      status: 'Active',
      lastLogin: '2025-10-24 14:30',
    },
    {
      id: 'AG102',
      name: 'Ramesh',
      role: 'Delivery',
      status: 'Inactive',
      lastLogin: '2025-10-23 11:20',
    },
    {
      id: 'AG103',
      name: 'Suresh',
      role: 'Support',
      status: 'Active',
      lastLogin: '2025-10-24 09:50',
    },
    {
      id: 'AG104',
      name: 'Meena',
      role: 'Admin',
      status: 'Active',
      lastLogin: '2025-10-24 13:15',
    },
  ];

  performAction(agent: Agent, action: string) {
    console.log(`Performing ${action} on ${agent.name}`);
    // Implement your actual action logic here
  }
}
