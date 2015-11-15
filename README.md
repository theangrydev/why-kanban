# Why Kanban?
This project implements a simple [Kanban](http://kanbanblog.com/explained) simulation to investigate the effects a Kanban approach can have on a software development pipeline.

Here are some key points I have learned so far by playing around with this:
* When there are no WIP limits and the team works slower than the new story rate, the average lead time increases unboundedly
* When there are WIP limits and the team works slower than the new story rate, the average lead time is bounded above by the maximal WIP limit divided by the overall throughput. This is a manifestation of [Little's Law](http://www.fabtime.com/ctwip.shtml).
* Regardless of WIP limits, throughput remains constant when the team works slower than the new story rate, provided that the team's behaviour is not influenced by the total amount of work in progress.
* Setting WIP limits too low will decrease the throughput if the team is capable of working at a rate faster than the WIP limits support.
