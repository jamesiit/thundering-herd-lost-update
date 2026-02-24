### Phase 3: Atomic Updates (Database-Level Concurrency)

**Goal:** Eliminate the database connection bottleneck caused by explicit locking strategies.

**The Strategy:**
Moved from **Application-Level Locking** (Optimistic/Pessimistic) to **Database-Level Atomicity**. Instead of locking a row, reading it, and then updating it (Read-Modify-Write), we push the logic directly into the database engine using a **Predicated Update**.

**Key Architectural Changes:**

1. **Workflow:**

* **Current (Atomic):** `Simulate Wait (Payment)` $\rightarrow$ `Open Tx` $\rightarrow$ `Atomic Update` $\rightarrow$ `Commit`. (Connection held only for microseconds).


2. **The "Guard Clause" SQL:**
* Replaced `findById()` + `save()` with a single JPQL query:
```sql
UPDATE Product p SET p.qty = p.qty - 1 WHERE p.id = :id AND p.qty > 0

```

* The `AND p.qty > 0` condition acts as an implicit lock and validation in one indivisible step.



**Outcome:**

* **Zero Blocking:** The "Wait" (simulated credit card check) occurs entirely in Java memory, without holding a database connection.
* **Fail-Fast:** The database returns `rows_affected = 0` immediately if stock is invalid, allowing for instant `SoldOutException` without retries.
* **Maximum Throughput:** HikariCP connections are cycled instantly, allowing a small pool to handle massive concurrency.