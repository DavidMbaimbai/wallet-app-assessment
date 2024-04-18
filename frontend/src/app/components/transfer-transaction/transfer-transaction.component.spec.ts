import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferTransactionComponent } from './transfer-transaction.component';

describe('TransferTransactionComponent', () => {
  let component: TransferTransactionComponent;
  let fixture: ComponentFixture<TransferTransactionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransferTransactionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TransferTransactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
